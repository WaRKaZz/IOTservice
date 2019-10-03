package service;

import database.UserDAO;
import exception.ConnectionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static util.ServiceManagement.isApplyPressed;

public class ProfileService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ConnectionException {
        if(isApplyPressed(request)){
            updatePassword(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/profile.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private void updatePassword(HttpServletRequest request, HttpServletResponse response){
        UserDAO userDAO = new UserDAO();
    }
}
