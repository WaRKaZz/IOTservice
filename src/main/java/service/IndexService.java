package service;

import database.UserDAO;
import entity.User;
import exception.ConnectionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class IndexService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,
                                                                                    SQLException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        User guest = userDAO.getUserByLogin("Guest");
        User sessionUser = (User) request.getSession().getAttribute("user");
        if(sessionUser == null) {
            request.getSession().setAttribute("user", guest);
            loginForward(request, response);
        } else if (sessionUser.equals(guest)){
            loginForward(request, response);
        } else {
            response.sendRedirect("/main");
        }
    }

    private void loginForward(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/loginPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
