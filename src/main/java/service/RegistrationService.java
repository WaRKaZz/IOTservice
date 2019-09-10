package service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegistrationService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        request.getSession().invalidate();
        String registrationMessage = "Please, fill registration form:";
        request.getSession().setAttribute("registrationMessage", registrationMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("registration.jsp");
        requestDispatcher.forward(request, response);
    }
}
