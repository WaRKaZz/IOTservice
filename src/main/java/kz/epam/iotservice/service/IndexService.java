package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.UserDAO;
import kz.epam.iotservice.entity.User;
import kz.epam.iotservice.exception.ConnectionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static kz.epam.iotservice.util.ConstantsForAttributes.*;
import static kz.epam.iotservice.util.ConstantsUri.MAIN_URI;
import static kz.epam.iotservice.util.JspConstants.LOGIN_PAGE_JSP;

public class IndexService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,
            SQLException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        User guest = userDAO.getUserByLogin(GUEST);
        User sessionUser = (User) request.getSession().getAttribute(USER_SESSION_STATEMENT);
        if(sessionUser == null) {
            request.getSession().setAttribute(USER_SESSION_STATEMENT, guest);
            loginForward(request, response);
        } else if (sessionUser.equals(guest)){
            loginForward(request, response);
        } else {
            response.sendRedirect(MAIN_URI);
        }
    }

    private void loginForward(HttpServletRequest request, HttpServletResponse response)
                                                                                  throws IOException, ServletException{
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(LOGIN_PAGE_JSP);
        requestDispatcher.forward(request, response);
    }
}
