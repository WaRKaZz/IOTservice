package service;

import database.UserDAO;
import entity.User;
import exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static validation.UserValidation.*;

public class RegistrationSubmitService implements Service {
    private User user = new User();
    private String registrationMessage;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        RequestDispatcher requestDispatcher;
        UserDAO userDAO = new UserDAO();
        if (isRegistrationCorrect(request)&&isUserNotExists(userDAO)){
           userDAO.addNewUser(user);
           userDAO.getUserByLogin(user.getUserLogin());
           request.getSession().setAttribute("user", user);
           requestDispatcher = request.getRequestDispatcher("jsp/mainPage.jsp");
        } else {
           request.getSession().setAttribute("registrationMessage", registrationMessage);
           requestDispatcher = request.getRequestDispatcher("jsp/registration.jsp");
        }
       requestDispatcher.forward(request, response);
    }

    private boolean isRegistrationCorrect(HttpServletRequest request){
        boolean isCorrect = true;
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String repeatedPassword = request.getParameter("repeatedPassword");
        String userFisetName = request.getParameter("firstName");
        String userlastName = request.getParameter("lastName");
        final int DEFAULT_USER_ROLE = 5;
        try {
            user.setUserLogin(validateLogin(login));
        } catch (ValidationException e){
            registrationMessage = "Login is incorrect";
            isCorrect = false;
        }
        try {
            if (!password.equals(repeatedPassword)){
                registrationMessage = "Password don't match";
                isCorrect = false;
            } else {
                user.setUserPassword(validatePassword(password));
            }
        } catch (ValidationException e){
            registrationMessage = "Password is incorrect";
            isCorrect = false;
        }
        try {
            user.setUserFirstName(validateUserFirstName(userFisetName));
            user.setUserLastName(validateUserLastName(userlastName));
        } catch (ValidationException e){
            registrationMessage = "You'r Name or Lastname is incorrect";
            isCorrect = false;
        }
        user.setUserRole(DEFAULT_USER_ROLE);
        return isCorrect;
    }

    private boolean isUserNotExists(UserDAO userDAO) throws SQLException{
        boolean isNotExists = false;
        User checkUser = userDAO.getUserByLogin(user.getUserLogin());
        if (checkUser.getUserID().equals(Long.parseLong("0"))){
            isNotExists = true;
        }
        registrationMessage = "Login is already EXISTS";
        return isNotExists;
    }
}
