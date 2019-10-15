package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.HomeDAO;
import kz.epam.iotservice.database.ConnectionPool;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.util.ConstantsForAttributes.*;
import static kz.epam.iotservice.util.ConstantsUri.ADD_NEW_HOME_URI;
import static kz.epam.iotservice.util.JspConstants.NEW_HOME_JSP;
import static kz.epam.iotservice.util.OtherConstants.EMPTY_STRING;
import static kz.epam.iotservice.util.OtherConstants.KEY_EMPTY;
import static kz.epam.iotservice.util.ServiceManagement.isApplyPressed;
import static kz.epam.iotservice.validation.HomeValidator.validateHomeAddress;
import static kz.epam.iotservice.validation.HomeValidator.validateHomeName;

public class AddNewHomeService implements Service {
    private static final String KEY_NEW_HOME_MESSAGE_VALID_DEVICE_ADDRESS = "key.newHomeMessageValidDeviceAddress";
    private static final String KEY_NEW_HOME_MESSAGE_SUCCESS = "key.newHomeMessageSuccess";
    private static final int ADMIN_IN_HOME_ROLE = 1;
    private static final String CANNOT_ADD_NEW_HOME_BY_MY_SQL = "Cannot add new home by MySQL";
    private static final Logger LOGGER = LogManager.getRootLogger();
    private String homeMessage = KEY_EMPTY;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)) {
            createNewHome(request, response);
        } else {
            request.setAttribute(HOME_MESSAGE_SESSION_STATEMENT, homeMessage);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(NEW_HOME_JSP);
            requestDispatcher.forward(request, response);
        }
    }

    private void refreshPage(HttpServletRequest request, HttpServletResponse response, List homeAdminList)
            throws IOException {
        request.setAttribute(HOME_MESSAGE_SESSION_STATEMENT, homeMessage);
        request.getSession().setAttribute(HOME_ADMIN_LIST_SESSION_STATEMENT, homeAdminList);
        response.sendRedirect(ADD_NEW_HOME_URI);
    }

    private void createNewHome(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        HomeDAO homeDAO = new HomeDAO();
        Home home = new Home();
        User user = (User) request.getSession().getAttribute(USER_SESSION_STATEMENT);
        String homeName = EMPTY_STRING;
        String homeAddress = EMPTY_STRING;
        boolean validationException = false;
        List homeAdminList = new ArrayList();
        Connection connection = ConnectionPool.getInstance().retrieve();
        try {
            homeName = validateHomeName(request.getParameter(HOME_NAME_PARAMETER));
            homeAddress = validateHomeAddress(request.getParameter(HOME_ADDRESS_PARAMETER));
        } catch (ValidationException e) {
            homeMessage = KEY_NEW_HOME_MESSAGE_VALID_DEVICE_ADDRESS;
            validationException = true;
        }
        try {
            if (!validationException) {
                home.setHomeName(homeName);
                home.setHomeAddress(homeAddress);
                Long newHomeID = homeDAO.addNewHome(home, connection);
                homeDAO.addHomeDependencyAdministrator(newHomeID, user.getUserID(), connection);
                homeMessage = KEY_NEW_HOME_MESSAGE_SUCCESS;
                homeAdminList = homeDAO.getHomeListByRole(user, ADMIN_IN_HOME_ROLE, connection);
                connection.commit();
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_ADD_NEW_HOME_BY_MY_SQL, e);
            connection.rollback();
        } finally {
            ConnectionPool.getInstance().putBack(connection);
        }
        refreshPage(request, response, homeAdminList);
    }
}
