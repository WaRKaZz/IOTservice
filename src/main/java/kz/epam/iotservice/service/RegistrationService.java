package kz.epam.iotservice.service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.epam.iotservice.constants.Attributes.REGISTRATION_MESSAGE_SESSION_STATEMENT;
import static kz.epam.iotservice.constants.Jsp.REGISTRATION_JSP;

public class RegistrationService implements Service {

    private static final String KEY_REGISTRATION_MESSAGE = "key.registrationMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getSession().invalidate();
        request.setAttribute(REGISTRATION_MESSAGE_SESSION_STATEMENT, KEY_REGISTRATION_MESSAGE);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(REGISTRATION_JSP);
        requestDispatcher.forward(request, response);
    }
}
