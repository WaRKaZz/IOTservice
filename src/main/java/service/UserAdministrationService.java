package service;

import database.UserDAO;
import entity.User;
import exception.ConnectionException;
import exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static util.ServiceManagement.isApplyPressed;
import static validation.UserValidation.validateID;


public class UserAdministrationService implements Service {

    String administrationMessage = "";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
                                throws IOException, ServletException, SQLException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        List users = userDAO.getUsersList();
        request.getSession().setAttribute("users", users);
        if(isApplyPressed(request)){
            banUser(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/userAdministration.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private void banUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        try {
            if (request.getParameter("action").equals("ban")) {
                userDAO.blockUserByID(validateID(request.getParameter("userID")));
                administrationMessage = "Success";

            } else if (request.getParameter("action").equals("unBan")){
                userDAO.unblockUserByID(validateID(request.getParameter("userID")));
                administrationMessage = "Success";
            }
            request.getSession().setAttribute("administrationMessage", administrationMessage);
            response.sendRedirect("/administration");
        } catch (ValidationException e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

