package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.UserDAO;
import kz.epam.iotservice.database.ConnectionPool;
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

import static kz.epam.iotservice.constants.Attributes.*;
import static kz.epam.iotservice.constants.Uri.MAIN_URI;
import static kz.epam.iotservice.constants.Jsp.BAD_LOGIN_JSP;
import static kz.epam.iotservice.util.ServiceManagement.isUserCorrect;

public class LoginService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CANNOT_GET_USER_BY_LOGIN_IN_MY_SQL = "Cannot get user by login in MySQL";
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
            IOException {
        request.getSession().setAttribute(USER_SESSION_STATEMENT, user);
        response.sendRedirect(MAIN_URI);
    }

    private boolean isUserNonBlocked() {
        return !user.getUserBlocked();
    }
}