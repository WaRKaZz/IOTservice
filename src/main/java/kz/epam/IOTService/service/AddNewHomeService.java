package kz.epam.IOTService.service;

import kz.epam.IOTService.database.HomeDAO;
import kz.epam.IOTService.entity.Home;
import kz.epam.IOTService.entity.User;
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
import static kz.epam.IOTService.validation.HomeValidator.validateHomeAddress;
import static kz.epam.IOTService.validation.HomeValidator.validateHomeName;

public class AddNewHomeService implements Service {
    private static final String KEY_NEW_HOME_MESSAGE_VALID_DEVICE_ADDRESS = "key.newHomeMessageValidDeviceAddress";
    private static final String KEY_NEW_HOME_MESSAGE_SUCCESS = "key.newHomeMessageSuccess";
    private String homeMessage = KEY_EMPTY;
    private static final int ADMIN_IN_HOME_ROLE = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)){
            createNewHome(request, response);
        } else {
            request.getSession().setAttribute(HOME_MESSAGE_SESSION_STATEMENT, homeMessage);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(NEW_HOME_JSP);
            requestDispatcher.forward(request, response);
        }
    }

    private void refreshPage(HttpServletRequest request, HttpServletResponse response, List homeAdminList)
            throws  IOException{
        request.getSession().setAttribute(HOME_MESSAGE_SESSION_STATEMENT, homeMessage);
        request.getSession().setAttribute(HOME_ADMIN_LIST_SESSION_STATEMENT, homeAdminList);
        response.sendRedirect(ADD_NEW_HOME_URI);
    }

    private void createNewHome(HttpServletRequest request, HttpServletResponse response)
            throws  IOException, SQLException, ConnectionException{
        HomeDAO homeDAO = new HomeDAO();
        Home home = new Home();
        User user = (User) request.getSession().getAttribute(USER_SESSION_STATEMENT);
        String homeName = EMPTY_STRING, homeAddress = EMPTY_STRING;
        boolean validationException = false;
        try{
            homeName = validateHomeName(request.getParameter(HOME_NAME_PARAMETER));
            homeAddress = validateHomeAddress(request.getParameter(HOME_ADDRESS_PARAMETER));
        } catch (ValidationException e){
            homeMessage = KEY_NEW_HOME_MESSAGE_VALID_DEVICE_ADDRESS;
            validationException = true;
        }
        if(!validationException){
            home.setHomeName(homeName);
            home.setHomeAddress(homeAddress);
            Long newHomeID = homeDAO.addNewHome(home);
            homeDAO.addHomeDependencyAdministrator(newHomeID, user.getUserID());
        }
        homeMessage = KEY_NEW_HOME_MESSAGE_SUCCESS;
        refreshPage(request, response, homeDAO.getHomeListByRole(user, ADMIN_IN_HOME_ROLE));
    }
}
