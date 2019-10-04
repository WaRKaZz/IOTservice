package kz.epam.IOTService.service;

import kz.epam.IOTService.util.IOTServiceConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.epam.IOTService.util.IOTServiceConstants.REGISTRATION_MESSAGE_SESSION_STATEMENT;

public class RegistrationService implements Service {

    private static final String KEY_REGISTRATION_MESSAGE = "key.registrationMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        request.getSession().invalidate();
        String registrationMessage = KEY_REGISTRATION_MESSAGE;
        request.getSession().setAttribute(REGISTRATION_MESSAGE_SESSION_STATEMENT, registrationMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(IOTServiceConstants.REGISTRATION_JSP);
        requestDispatcher.forward(request, response);
    }
}
