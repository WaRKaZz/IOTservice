package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.HomeDAO;
import kz.epam.iotservice.dao.UserDAO;
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
import static kz.epam.iotservice.util.ConstantsUri.MAIN_URI;
import static kz.epam.iotservice.util.JspConstants.BAD_LOGIN_JSP;
import static kz.epam.iotservice.util.ServiceManagement.isUserCorrect;

public class LoginService implements Service {
    private static final int ADMIN_IN_HOME_ROLE = 1;
    private static final int USER_IN_HOME_ROLE = 2;
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CANNOT_GET_USER_BY_LOGIN_IN_MY_SQL = "Cannot get user by login in MySQL";
    private static final String CANNOT_GET_LISTS_OF_USER_RULES_OF_THIS_USER = "Cannot get Lists of user rules of this user";
    private User user;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException, SQLException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        String login = request.getParameter(LOGIN_PARAMETER);
        Connection connection = ConnectionPool.getInstance().retrieve();
        try {
            user = userDAO.getUserByLogin(login, connection);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_USER_BY_LOGIN_IN_MY_SQL, e);
            connection.rollback();
        } finally {
            ConnectionPool.getInstance().putBack(connection);
        }
        String password = request.getParameter(PASSWORD_PARAMETER);
        if (isUserCorrect(user, password) && (isUserNonBlocked())) {
            loginAndLoadAttributes(request, response);
        } else {
            response.setHeader(REFRESH_HEADER_STATUS, REDIRECT_IN_5_SECONDS_HEADER_STATUS);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(BAD_LOGIN_JSP);
            requestDispatcher.forward(request, response);
        }

    }

    private void loginAndLoadAttributes(HttpServletRequest request, HttpServletResponse response) throws
            IOException, SQLException, ConnectionException {
        HomeDAO homeDAO = new HomeDAO();
        Connection connection = ConnectionPool.getInstance().retrieve();
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
        request.getSession().setAttribute(USER_SESSION_STATEMENT, user);
        response.sendRedirect(MAIN_URI);
    }

    private boolean isUserNonBlocked() {
        return !user.getUserBlocked();
    }
}