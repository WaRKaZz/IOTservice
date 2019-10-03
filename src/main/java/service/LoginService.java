package service;

import database.HomeDAO;
import database.UserDAO;
import entity.Home;
import entity.User;
import exception.ConnectionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoginService implements Service {
    private static final int ADMIN_IN_HOME_ROLE = 1;
    private static final int USER_IN_HOME_ROLE = 2;
    private User user;
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException, SQLException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        String login = request.getParameter("login");
        user = userDAO.getUserByLogin(login);
        String password = request.getParameter("password");
        if (isUserCorrect(user, password)&&(isUserNonBlocked(user))){
            loginAndLoadAttributes(request, response);
        } else {
            response.setHeader("Refresh", "5;/");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/badLogin.jsp");
            requestDispatcher.forward(request, response);
        }

    }

    private void loginAndLoadAttributes(HttpServletRequest request, HttpServletResponse response)throws
            IOException, ServletException, SQLException, ConnectionException{
        HomeDAO homeDAO = new HomeDAO();
        List<Home> homeAdminList = homeDAO.getHomeListByRole(user, ADMIN_IN_HOME_ROLE);
        List<Home> homeUserList = homeDAO.getHomeListByRole(user, USER_IN_HOME_ROLE);
        request.getSession().setAttribute("homeAdminList", homeAdminList);
        request.getSession().setAttribute("homeUserList", homeUserList);
        request.getSession().setAttribute("user", user);
        response.sendRedirect("/main");
    }

    private boolean isUserCorrect (User user, String password){
        boolean isCorrect = false;
        if (user.getUserPassword().equals(password)){
            isCorrect = true;
        }
        return isCorrect;
    }

    private boolean isUserNonBlocked (User user){
        return !user.getUserBlocked();
    }
}