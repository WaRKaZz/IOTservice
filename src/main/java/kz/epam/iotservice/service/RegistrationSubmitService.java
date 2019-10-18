package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.UserDAO;
import kz.epam.iotservice.database.ConnectionPool;
import kz.epam.iotservice.entity.User;
import kz.epam.iotservice.exception.ConnectionException;
import kz.epam.iotservice.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static kz.epam.iotservice.constants.Attributes.*;
import static kz.epam.iotservice.constants.Jsp.DEVICE_VIEW_JSP;
import static kz.epam.iotservice.constants.Jsp.REGISTRATION_JSP;
import static kz.epam.iotservice.constants.Other.KEY_EMPTY;
import static kz.epam.iotservice.constants.Other.STRING_ZERO;
import static kz.epam.iotservice.util.ServiceManagement.encryptPassword;
import static kz.epam.iotservice.validation.UserValidation.validateLogin;
import static kz.epam.iotservice.validation.UserValidation.validatePassword;

public class RegistrationSubmitService implements Service {
    private static final String KEY_REGISTRATION_MESSAGE_LOGIN_INCORRECT = "key.registrationMessageLoginIncorrect";
    private static final String KEY_REGISTRATION_MESSAGE_PASSWORD_MATCH = "key.registrationMessagePasswordMatch";
    private static final String KEY_REGISTRATION_MESSAGE_PASSWORD_INCORRECT = "key.registrationMessagePasswordIncorrect";
    private static final String KEY_REGISTRATION_MESSAGE_LOGIN_EXISTS = "key.registrationMessageLoginExists";
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CANNOT_CREATE_NEW_USER_SOME_MY_SQL_PROBLEM = "Cannot create new User, some MySQL problem";
    private static final String CANNOT_CHECK_USER_MY_SQL_PROBLEM_REGISTRATION_SUBMIT_SERVICE = "Cannot check user, MySQL problem (Registration submit service)";
    private final User user = new User();
    private String registrationMessage = KEY_EMPTY;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
        RequestDispatcher requestDispatcher;
        UserDAO userDAO = new UserDAO();
        if (isRegistrationCorrect(request) && isUserNotExists(userDAO)) {
            Connection connection = ConnectionPool.getInstance().retrieve();
            try {
                userDAO.addNewUser(user, connection);
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error(CANNOT_CREATE_NEW_USER_SOME_MY_SQL_PROBLEM, e);
                connection.rollback();
            } finally {
                ConnectionPool.getInstance().putBack(connection);
            }
            request.getSession().setAttribute(USER_SESSION_STATEMENT, user);
            requestDispatcher = request.getRequestDispatcher(DEVICE_VIEW_JSP);
        } else {
            request.setAttribute(REGISTRATION_MESSAGE_SESSION_STATEMENT, registrationMessage);
            requestDispatcher = request.getRequestDispatcher(REGISTRATION_JSP);
        }
        requestDispatcher.forward(request, response);
    }

    private boolean isRegistrationCorrect(HttpServletRequest request) {
        boolean isCorrect = true;
        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        String repeatedPassword = request.getParameter(REPEATED_PASSWORD_PARAMETER);
        final int DEFAULT_USER_ROLE = 5;
        try {
            user.setUserLogin(validateLogin(login));
        } catch (ValidationException e) {
            registrationMessage = KEY_REGISTRATION_MESSAGE_LOGIN_INCORRECT;
            isCorrect = false;
        }
        try {
            if (!password.equals(repeatedPassword)) {
                registrationMessage = KEY_REGISTRATION_MESSAGE_PASSWORD_MATCH;
                isCorrect = false;
            } else {
                user.setUserPassword(encryptPassword(validatePassword(password)));
            }
        } catch (ValidationException e) {
            registrationMessage = KEY_REGISTRATION_MESSAGE_PASSWORD_INCORRECT;
            isCorrect = false;
        }
        user.setUserRole(DEFAULT_USER_ROLE);
        return isCorrect;
    }

    private boolean isUserNotExists(UserDAO userDAO) throws SQLException, ConnectionException {
        boolean isNotExists = false;
        Connection connection = ConnectionPool.getInstance().retrieve();
        User checkUser = new User();
        try {
            checkUser = userDAO.getUserByLogin(user.getUserLogin(), connection);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_CHECK_USER_MY_SQL_PROBLEM_REGISTRATION_SUBMIT_SERVICE, e);
            connection.rollback();
        } finally {
            ConnectionPool.getInstance().putBack(connection);
        }
        if (checkUser.getUserID().equals(Long.parseLong(STRING_ZERO))) {
            isNotExists = true;
        }
        registrationMessage = KEY_REGISTRATION_MESSAGE_LOGIN_EXISTS;
        return isNotExists;
    }
}
