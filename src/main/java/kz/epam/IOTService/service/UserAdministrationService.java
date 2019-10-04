package kz.epam.IOTService.service;

import kz.epam.IOTService.database.UserDAO;
import kz.epam.IOTService.exception.ConnectionException;
import kz.epam.IOTService.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static kz.epam.IOTService.util.IOTServiceConstants.*;
import static kz.epam.IOTService.util.ServiceManagement.isApplyPressed;
import static kz.epam.IOTService.validation.UserValidation.validateID;


public class UserAdministrationService implements Service {

    private static final String ACTION = "action";
    private static final String BAN = "ban";
    private static final String UN_BAN = "unBan";
    private static final String KEY_ADMINISTRATION_MESSAGE_SUCCESS_BAN = "key.administrationMessageSuccessBan";
    private static final String KEY_ADMINISTRATION_MESSAGE_SUCCESS_UNBAN = "key.administrationMessageSuccessUnban";
    private String administrationMessage = KEY_EMPTY;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        List users = userDAO.getUsersList();
        request.getSession().setAttribute(USERS_SESSION_STATEMENT, users);
        if(isApplyPressed(request)){
            banUser(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(USER_ADMINISTRATION_JSP);
            request.getSession().setAttribute(ADMINISTRATION_MESSAGE_SESSION_STATEMENT, administrationMessage);
            requestDispatcher.forward(request, response);
        }
    }

    private void banUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        try {
            if (request.getParameter(ACTION).equals(BAN)) {
                userDAO.blockUserByID(validateID(request.getParameter(USER_ID_PARAMETER)));
                administrationMessage = KEY_ADMINISTRATION_MESSAGE_SUCCESS_BAN;
            } else if (request.getParameter(ACTION).equals(UN_BAN)){
                userDAO.unblockUserByID(validateID(request.getParameter(USER_ID_PARAMETER)));
                administrationMessage = KEY_ADMINISTRATION_MESSAGE_SUCCESS_UNBAN;
            }
            request.getSession().setAttribute(ADMINISTRATION_MESSAGE_SESSION_STATEMENT, administrationMessage);
            response.sendRedirect(ADMINISTRATION_URI);
        } catch (ValidationException e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

