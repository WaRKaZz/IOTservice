package kz.epam.IOTService.service;

import kz.epam.IOTService.database.UserDAO;
import kz.epam.IOTService.entity.User;
import kz.epam.IOTService.exception.ConnectionException;
import kz.epam.IOTService.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static kz.epam.IOTService.util.IOTServiceConstants.*;
import static kz.epam.IOTService.util.ServiceManagement.encryptPassword;
import static kz.epam.IOTService.validation.UserValidation.validateLogin;
import static kz.epam.IOTService.validation.UserValidation.validatePassword;

public class RegistrationSubmitService implements Service {
    private static final String KEY_REGISTRATION_MESSAGE_LOGIN_INCORRECT = "key.registrationMessageLoginIncorrect";
    private static final String KEY_REGISTRATION_MESSAGE_PASSWORD_MATCH = "key.registrationMessagePasswordMatch";
    private static final String KEY_REGISTRATION_MESSAGE_PASSWORD_INCORRECT = "key.registrationMessagePasswordIncorrect";
    private static final String KEY_REGISTRATION_MESSAGE_LOGIN_EXISTS = "key.registrationMessageLoginExists";
    private final User user = new User();
    private String registrationMessage = KEY_EMPTY;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
        RequestDispatcher requestDispatcher;
        UserDAO userDAO = new UserDAO();
        if (isRegistrationCorrect(request)&&isUserNotExists(userDAO)){
            userDAO.addNewUser(user);
            userDAO.getUserByLogin(user.getUserLogin());
            request.getSession().setAttribute(USER_SESSION_STATEMENT, user);
            requestDispatcher = request.getRequestDispatcher(DEVICE_VIEW_JSP);
        } else {
            request.getSession().setAttribute(REGISTRATION_MESSAGE_SESSION_STATEMENT, registrationMessage);
            requestDispatcher = request.getRequestDispatcher(REGISTRATION_JSP);
        }
        requestDispatcher.forward(request, response);
    }

    private boolean isRegistrationCorrect(HttpServletRequest request){
        boolean isCorrect = true;
        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        String repeatedPassword = request.getParameter(REPEATED_PASSWORD_PARAMETER);
        final int DEFAULT_USER_ROLE = 5;
        try {
            user.setUserLogin(validateLogin(login));
        } catch (ValidationException e){
            registrationMessage = KEY_REGISTRATION_MESSAGE_LOGIN_INCORRECT;
            isCorrect = false;
        }
        try {
            if (!password.equals(repeatedPassword)){
                registrationMessage = KEY_REGISTRATION_MESSAGE_PASSWORD_MATCH;
                isCorrect = false;
            } else {
                user.setUserPassword(encryptPassword(validatePassword(password)));
            }
        } catch (ValidationException e){
            registrationMessage = KEY_REGISTRATION_MESSAGE_PASSWORD_INCORRECT;
            isCorrect = false;
        }
        user.setUserRole(DEFAULT_USER_ROLE);
        return isCorrect;
    }

    private boolean isUserNotExists(UserDAO userDAO) throws SQLException, ConnectionException {
        boolean isNotExists = false;
        User checkUser = userDAO.getUserByLogin(user.getUserLogin());
        if (checkUser.getUserID().equals(Long.parseLong(STRING_ZERO))){
            isNotExists = true;
        }
        registrationMessage = KEY_REGISTRATION_MESSAGE_LOGIN_EXISTS;
        return isNotExists;
    }
}
