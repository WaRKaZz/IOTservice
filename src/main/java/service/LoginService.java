package service;
import database.UserDAO;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class LoginService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        UserDAO userDAO = new UserDAO();
        String login = request.getParameter("login");
        User user = userDAO.getUserByLogin(login);
        String password = request.getParameter("password");
        if (isUserCorrect(user, password)){
            request.getSession().setAttribute("user", user);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("main_page.jsp");
            requestDispatcher.forward(request, response);
        } else {
            response.setHeader("Refresh", "5;/");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("bad_login.jsp");
            requestDispatcher.forward(request, response);
        }

    }

    private boolean isUserCorrect (User user, String password){
        boolean isCorrect = false;
        if (user.getUserPassword().equals(password)){
            isCorrect = true;
        }
        return isCorrect;
    }
}