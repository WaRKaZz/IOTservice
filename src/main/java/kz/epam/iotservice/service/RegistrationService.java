package kz.epam.iotservice.service;

import kz.epam.iotservice.util.IOTServiceConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.epam.iotservice.util.IOTServiceConstants.REGISTRATION_MESSAGE_SESSION_STATEMENT;

public class RegistrationService implements Service {

    private static final String KEY_REGISTRATION_MESSAGE = "key.registrationMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        request.getSession().invalidate();
        request.getSession().setAttribute(REGISTRATION_MESSAGE_SESSION_STATEMENT, KEY_REGISTRATION_MESSAGE);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(IOTServiceConstants.REGISTRATION_JSP);
        requestDispatcher.forward(request, response);
    }
}
