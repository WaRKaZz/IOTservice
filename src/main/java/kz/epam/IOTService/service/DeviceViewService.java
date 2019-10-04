package kz.epam.IOTService.service;

import kz.epam.IOTService.exception.ConnectionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static kz.epam.IOTService.util.IOTServiceConstants.*;
import static kz.epam.IOTService.util.ServiceManagement.updateHomeInSession;

public class DeviceViewService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ConnectionException {
        if (request.getSession().getAttribute(HOME_SESSION_STATEMENT) != null){
            updateHomeInSession(request);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(DEVICE_VIEW_JSP);
            requestDispatcher.forward(request, response);
        } else {
            request.getSession().setAttribute(DEVICE_MESSAGE_SESSION_STATEMENT, KEY_EMPTY);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(UPDATE_DEVICE_JSP);
            requestDispatcher.forward(request, response);
        }
    }
}
