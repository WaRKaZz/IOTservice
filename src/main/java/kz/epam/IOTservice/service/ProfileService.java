package kz.epam.IOTservice.service;

import kz.epam.IOTservice.database.UserDAO;
import kz.epam.IOTservice.entity.User;
import kz.epam.IOTservice.exception.ConnectionException;
import kz.epam.IOTservice.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static kz.epam.IOTservice.util.ServiceManagement.isApplyPressed;
import static kz.epam.IOTservice.validation.UserValidation.validatePassword;

public class ProfileService implements Service {

    private String changePasswordMessage = "";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
                                             throws IOException, ServletException, SQLException, ConnectionException {
        if(isApplyPressed(request)){
            updatePassword(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/profile.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private void updatePassword(HttpServletRequest request, HttpServletResponse response)
                                                      throws ConnectionException, SQLException, IOException{
        UserDAO userDAO = new UserDAO();
        User user = (User) request.getSession().getAttribute("user");
        boolean validationException = false;
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String repeatedPassword = request.getParameter("repeatedPassword");
        if (isUserCorrect(user, oldPassword)){
            try {
                if(newPassword.equals(repeatedPassword)){
                    user.setUserPassword(validatePassword(newPassword));
                }
            } catch (ValidationException e){
                changePasswordMessage = "Password don't correct";
                validationException = true;
            }
        }
        if (!validationException){
            userDAO.updateUser(user);
            changePasswordMessage = "Password changed successfully";
        }
        refreshPage(request, response);
    }

    private void refreshPage (HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        request.getSession().setAttribute("changePasswordMessage", changePasswordMessage);
        response.sendRedirect("/profile");
    }

    private boolean isUserCorrect (User user, String password){
        boolean isCorrect = false;
        if (user.getUserPassword().equals(password)){
            isCorrect = true;
        }
        return isCorrect;
    }
}
