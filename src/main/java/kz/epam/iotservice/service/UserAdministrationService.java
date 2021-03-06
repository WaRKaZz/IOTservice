package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.UserDAO;
import kz.epam.iotservice.database.ConnectionPool;
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
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.constants.Attributes.*;
import static kz.epam.iotservice.constants.Uri.ADMINISTRATION_URI;
import static kz.epam.iotservice.constants.Jsp.USER_ADMINISTRATION_JSP;
import static kz.epam.iotservice.constants.Other.KEY_EMPTY;
import static kz.epam.iotservice.util.ServiceManagement.isApplyPressed;
import static kz.epam.iotservice.validation.UserValidation.validateID;


public class UserAdministrationService implements Service {

    private static final String ACTION = "action";
    private static final String BAN = "ban";
    private static final String UN_BAN = "unBan";
    private static final String KEY_ADMINISTRATION_MESSAGE_SUCCESS_BAN = "key.administrationMessageSuccessBan";
    private static final String KEY_ADMINISTRATION_MESSAGE_SUCCESS_UNBAN = "key.administrationMessageSuccessUnban";
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String SOMETHING_WENT_WRONG_IN_BAN_OR_UNBAN_SERVICE = "Something went wrong in Ban or Unban service";
    private static final String CANNOT_GET_USER_LIST_IN_USER_ADMINISTRATION_SERVICE = "Cannot get user list in User Administration Service";
    private String administrationMessage = KEY_EMPTY;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        Connection connection = ConnectionPool.getInstance().retrieve();
        List users = new ArrayList();
        try {
            users = userDAO.getUsersList(connection);
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_USER_LIST_IN_USER_ADMINISTRATION_SERVICE, e);
            connection.rollback();
        } finally {
            ConnectionPool.getInstance().putBack(connection);
        }
        request.setAttribute(USERS_SESSION_STATEMENT, users);
        if (isApplyPressed(request)) {
            banUser(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(USER_ADMINISTRATION_JSP);
            request.setAttribute(ADMINISTRATION_MESSAGE_SESSION_STATEMENT, administrationMessage);
            requestDispatcher.forward(request, response);
        }
    }

    private void banUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        Connection connection = ConnectionPool.getInstance().retrieve();
        try {
            if (request.getParameter(ACTION).equals(BAN)) {
                userDAO.blockUserByID(validateID(request.getParameter(USER_ID_PARAMETER)), connection);
                administrationMessage = KEY_ADMINISTRATION_MESSAGE_SUCCESS_BAN;
            } else if (request.getParameter(ACTION).equals(UN_BAN)) {
                userDAO.unblockUserByID(validateID(request.getParameter(USER_ID_PARAMETER)), connection);
                administrationMessage = KEY_ADMINISTRATION_MESSAGE_SUCCESS_UNBAN;
            }
            request.setAttribute(ADMINISTRATION_MESSAGE_SESSION_STATEMENT, administrationMessage);
            response.sendRedirect(ADMINISTRATION_URI);
        } catch (ValidationException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            LOGGER.error(SOMETHING_WENT_WRONG_IN_BAN_OR_UNBAN_SERVICE, e);
            connection.rollback();
        } finally {
            ConnectionPool.getInstance().putBack(connection);
        }
    }
}

