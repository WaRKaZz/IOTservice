package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.HomeDAO;
import kz.epam.iotservice.entity.Home;
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
import java.sql.SQLException;
import java.util.List;

import static kz.epam.iotservice.util.ConstantsForAttributes.*;
import static kz.epam.iotservice.util.ConstantsUri.UPDATE_HOME_URI;
import static kz.epam.iotservice.util.JspConstants.UPDATE_HOME_JSP;
import static kz.epam.iotservice.util.OtherConstants.*;
import static kz.epam.iotservice.util.ServiceManagement.isApplyPressed;
import static kz.epam.iotservice.validation.HomeValidator.*;

public class UpdateHomeService implements Service {
    private static final String KEY_UPDATE_HOME_MESSAGE_INCORRECT_ID = "key.updateHomeMessageIncorrectID";
    private static final String KEY_UPDATE_HOME_MESSAGE_SUCCESS_DELETE = "key.updateHomeMessageSuccessDelete";
    private static final String KEY_UPDATE_HOME_MESSAGE_INCORRECT_DATA = "key.updateHomeMessageIncorrectData";
    private static final String KEY_UPDATE_HOME_MESSAGE_SUCCESS_UPDATE = "key.updateHomeMessageSuccessUpdate";
    private static final int ADMIN_ROLE = 1;
    private static final Logger LOGGER = LogManager.getRootLogger();
    private final HomeDAO homeDAO = new HomeDAO();
    private String homeMessage = KEY_EMPTY;

    private static boolean isDeleteHomePressed(HttpServletRequest request) {
        if (request.getParameter(DELETE_PARAMETER) != null) {
            return request.getParameter(DELETE_PARAMETER).equals(TRUE);
        } else {
            return false;
        }
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)) {
            if (isDeleteHomePressed(request)) {
                deleteHome(request, response);
            } else {
                updateHome(request, response);
            }
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(UPDATE_HOME_JSP);
            request.setAttribute(HOME_MESSAGE_SESSION_STATEMENT, homeMessage);
            requestDispatcher.forward(request, response);
        }
    }

    private void refreshPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        User user = (User) request.getSession().getAttribute(USER_SESSION_STATEMENT);
        List<Home> homeAdminList = homeDAO.getHomeListByRole(user, ADMIN_ROLE);
        request.getSession().setAttribute(HOME_ADMIN_LIST_SESSION_STATEMENT, homeAdminList);
        request.setAttribute(HOME_MESSAGE_SESSION_STATEMENT, homeMessage);
        response.sendRedirect(UPDATE_HOME_URI);
    }

    private void deleteHome(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        HomeDAO homeDAO = new HomeDAO();
        Long homeID = (long) 0;
        boolean validationException = false;
        try {
            homeID = validateID(request.getParameter(HOME_ID_PARAMETER));
        } catch (ValidationException e) {
            homeMessage = KEY_UPDATE_HOME_MESSAGE_INCORRECT_ID;
            validationException = true;
        }
        if (!validationException) {
            homeDAO.deleteHome(homeID);
            homeMessage = KEY_UPDATE_HOME_MESSAGE_SUCCESS_DELETE;
        }
        refreshPage(request, response);
    }

    private void updateHome(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        Home home = new Home();
        Long homeID = (long) 0;
        boolean validationException = false;
        String homeName = EMPTY_STRING;
        String homeAddress = EMPTY_STRING;
        try {
            homeID = validateID(request.getParameter(HOME_ID_PARAMETER));
            homeAddress = validateHomeAddress(request.getParameter(HOME_ADDRESS_PARAMETER));
            homeName = validateHomeName(request.getParameter(HOME_NAME_PARAMETER));
        } catch (ValidationException e) {
            LOGGER.error(e);
            LOGGER.error("Can not update Home");
            homeMessage = KEY_UPDATE_HOME_MESSAGE_INCORRECT_DATA;
            validationException = true;
        }
        if (!validationException) {
            home.setHomeAddress(homeAddress);
            home.setHomeName(homeName);
            home.setHomeID(homeID);
            homeDAO.updateHome(home);
            homeMessage = KEY_UPDATE_HOME_MESSAGE_SUCCESS_UPDATE;
        }
        refreshPage(request, response);
    }
}
