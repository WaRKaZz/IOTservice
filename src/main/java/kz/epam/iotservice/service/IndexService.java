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

import static kz.epam.iotservice.util.ConstantsForAttributes.GUEST;
import static kz.epam.iotservice.util.ConstantsForAttributes.USER_SESSION_STATEMENT;
import static kz.epam.iotservice.util.ConstantsUri.MAIN_URI;
import static kz.epam.iotservice.util.JspConstants.LOGIN_PAGE_JSP;

public class IndexService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CANNOT_GET_GUEST_USER_IN_INDEX_SERVICE = "Cannot get guest user in index service";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,
            SQLException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        Connection connection = ConnectionPool.getInstance().retrieve();
        User guest = new User();
        try {
            guest = userDAO.getUserByLogin(GUEST, connection);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_GUEST_USER_IN_INDEX_SERVICE, e);
            connection.rollback();
        } finally {
            ConnectionPool.getInstance().putBack(connection);
        }
        User sessionUser = (User) request.getSession().getAttribute(USER_SESSION_STATEMENT);
        if (sessionUser == null) {
            request.getSession().setAttribute(USER_SESSION_STATEMENT, guest);
            loginForward(request, response);
        } else if (sessionUser.equals(guest)) {
            loginForward(request, response);
        } else {
            response.sendRedirect(MAIN_URI);
        }
    }

    private void loginForward(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(LOGIN_PAGE_JSP);
        requestDispatcher.forward(request, response);
    }
}
