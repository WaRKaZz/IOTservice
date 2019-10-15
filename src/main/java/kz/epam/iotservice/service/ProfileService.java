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

import static kz.epam.iotservice.util.ConstantsForAttributes.*;
import static kz.epam.iotservice.util.ConstantsUri.PROFILE_URI;
import static kz.epam.iotservice.util.JspConstants.PROFILE_JSP;
import static kz.epam.iotservice.util.OtherConstants.KEY_EMPTY;
import static kz.epam.iotservice.util.ServiceManagement.isApplyPressed;
import static kz.epam.iotservice.util.ServiceManagement.isUserCorrect;
import static kz.epam.iotservice.validation.UserValidation.validatePassword;

public class ProfileService implements Service {

    private static final String KEY_PROFILE_MESSAGE_ERROR_PASSWORD = "key.profileMessageErrorPassword";
    private static final String KEY_PROFILE_MESSAGE_SUCCESS = "key.profileMessageSuccess";
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CANNOT_UPDATE_THIS_USER_IN_PROFILE_SERVICE = "Cannot update this user in Profile Service";
    private String changePasswordMessage = KEY_EMPTY;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)) {
            updatePassword(request, response);
        } else {
            request.setAttribute(CHANGE_PASSWORD_MESSAGE_SESSION_STATEMENT, changePasswordMessage);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_JSP);
            requestDispatcher.forward(request, response);
        }
    }

    private void updatePassword(HttpServletRequest request, HttpServletResponse response)
            throws ConnectionException, SQLException, IOException {
        UserDAO userDAO = new UserDAO();
        User user = (User) request.getSession().getAttribute(USER_SESSION_STATEMENT);
        boolean validationException = false;
        String oldPassword = request.getParameter(OLD_PASSWORD_PARAMETER);
        String newPassword = request.getParameter(NEW_PASSWORD_PARAMETER);
        String repeatedPassword = request.getParameter(REPEATED_PASSWORD_PARAMETER);
        if (isUserCorrect(user, oldPassword)) {
            try {
                if (newPassword.equals(repeatedPassword)) {
                    user.setUserPassword(validatePassword(newPassword));
                }
            } catch (ValidationException e) {
                changePasswordMessage = KEY_PROFILE_MESSAGE_ERROR_PASSWORD;
                validationException = true;
            }
        }
        if (!validationException) {
            Connection connection = ConnectionPool.getInstance().retrieve();
            try {
                userDAO.updateUser(user, connection);
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error(CANNOT_UPDATE_THIS_USER_IN_PROFILE_SERVICE, e);
                connection.rollback();
            } finally {
                ConnectionPool.getInstance().putBack(connection);
            }
            changePasswordMessage = KEY_PROFILE_MESSAGE_SUCCESS;
        }
        refreshPage(request, response);
    }

    private void refreshPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setAttribute(CHANGE_PASSWORD_MESSAGE_SESSION_STATEMENT, changePasswordMessage);
        response.sendRedirect(PROFILE_URI);
    }


}
