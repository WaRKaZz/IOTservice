package kz.epam.IOTService.service;

import kz.epam.IOTService.database.UserDAO;
import kz.epam.IOTService.entity.User;
import kz.epam.IOTService.exception.ConnectionException;
import kz.epam.IOTService.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static kz.epam.IOTService.util.ServiceManagement.encryptPassword;
import static kz.epam.IOTService.validation.UserValidation.validateLogin;
import static kz.epam.IOTService.validation.UserValidation.validatePassword;

public class RegistrationSubmitService implements Service {
    private User user = new User();
    private String registrationMessage = "key.empty";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
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
        final int DEFAULT_USER_ROLE = 5;
        try {
            user.setUserLogin(validateLogin(login));
        } catch (ValidationException e){
            registrationMessage = "key.registrationMessageLoginIncorrect";
            isCorrect = false;
        }
        try {
            if (!password.equals(repeatedPassword)){
                registrationMessage = "key.registrationMessagePasswordMatch";
                isCorrect = false;
            } else {
                user.setUserPassword(encryptPassword(validatePassword(password)));
            }
        } catch (ValidationException e){
            registrationMessage = "key.registrationMessagePasswordIncorrect";
            isCorrect = false;
        }
        user.setUserRole(DEFAULT_USER_ROLE);
        return isCorrect;
    }

    private boolean isUserNotExists(UserDAO userDAO) throws SQLException, ConnectionException {
        boolean isNotExists = false;
        User checkUser = userDAO.getUserByLogin(user.getUserLogin());
        if (checkUser.getUserID().equals(Long.parseLong("0"))){
            isNotExists = true;
        }
        registrationMessage = "key.registrationMessageLoginExists";
        return isNotExists;
    }
}
