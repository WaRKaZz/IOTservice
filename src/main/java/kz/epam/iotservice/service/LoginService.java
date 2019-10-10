package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.HomeDAO;
import kz.epam.iotservice.dao.UserDAO;
import kz.epam.iotservice.entity.Home;
import kz.epam.iotservice.entity.User;
import kz.epam.iotservice.exception.ConnectionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static kz.epam.iotservice.util.IOTServiceConstants.*;
import static kz.epam.iotservice.util.ServiceManagement.isUserCorrect;

public class LoginService implements Service {
    private static final int ADMIN_IN_HOME_ROLE = 1;
    private static final int USER_IN_HOME_ROLE = 2;
    private User user;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException, SQLException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        String login = request.getParameter(LOGIN_PARAMETER);
        user = userDAO.getUserByLogin(login);
        String password = request.getParameter(PASSWORD_PARAMETER);
        if (isUserCorrect(user, password)&&(isUserNonBlocked(user))){
            loginAndLoadAttributes(request, response);
        } else {
            response.setHeader(REFRESH_HEADER_STATUS, REDIRECT_IN_5_SECONDS_HEADER_STATUS);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(BAD_LOGIN_JSP);
            requestDispatcher.forward(request, response);
        }

    }

    private void loginAndLoadAttributes(HttpServletRequest request, HttpServletResponse response)throws
            IOException, SQLException, ConnectionException{
        HomeDAO homeDAO = new HomeDAO();
        List<Home> homeAdminList = homeDAO.getHomeListByRole(user, ADMIN_IN_HOME_ROLE);
        List<Home> homeUserList = homeDAO.getHomeListByRole(user, USER_IN_HOME_ROLE);
        request.getSession().setAttribute(HOME_ADMIN_LIST_SESSION_STATEMENT, homeAdminList);
        request.getSession().setAttribute(HOME_USER_LIST_SESSION_STATEMENT, homeUserList);
        request.getSession().setAttribute(USER_SESSION_STATEMENT, user);
        response.sendRedirect(MAIN_URI);
    }

    private boolean isUserNonBlocked (User user){
        return !user.getUserBlocked();
    }
}