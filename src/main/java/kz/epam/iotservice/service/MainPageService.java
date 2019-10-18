package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.HomeDAO;
import kz.epam.iotservice.database.ConnectionPool;
import kz.epam.iotservice.entity.Home;
import kz.epam.iotservice.entity.User;
import kz.epam.iotservice.exception.ConnectionException;
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
import static kz.epam.iotservice.util.JspConstants.MAIN_PAGE_JSP;
import static kz.epam.iotservice.util.ServiceManagement.loadHomeIntoRequest;


public class MainPageService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final int ADMIN_IN_HOME_ROLE = 1;
    private static final int USER_IN_HOME_ROLE = 2;
    private static final String CANNOT_GET_LISTS_OF_USER_RULES_OF_THIS_USER = "Cannot get Lists of user rules of this user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
        loadHomeList(request);
        loadHomeIntoRequest(request);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(MAIN_PAGE_JSP);
        requestDispatcher.forward(request, response);
    }

    private void loadHomeList(HttpServletRequest request) throws SQLException, ConnectionException {
        HomeDAO homeDAO = new HomeDAO();
        Connection connection = ConnectionPool.getInstance().retrieve();
        User user = (User) request.getSession().getAttribute(USER_SESSION_STATEMENT);
        List<Home> homeAdminList = new ArrayList<>();
        List<Home> homeUserList = new ArrayList<>();
        try {
            homeAdminList = homeDAO.getHomeListByRole(user, ADMIN_IN_HOME_ROLE, connection);
            homeUserList = homeDAO.getHomeListByRole(user, USER_IN_HOME_ROLE, connection);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_LISTS_OF_USER_RULES_OF_THIS_USER, e);
            connection.rollback();
        } finally {
            ConnectionPool.getInstance().putBack(connection);
        }
        request.getSession().setAttribute(HOME_ADMIN_LIST_SESSION_STATEMENT, homeAdminList);
        request.getSession().setAttribute(HOME_USER_LIST_SESSION_STATEMENT, homeUserList);
    }
}
