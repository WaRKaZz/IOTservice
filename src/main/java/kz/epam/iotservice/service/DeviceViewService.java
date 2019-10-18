package kz.epam.iotservice.service;

import kz.epam.iotservice.exception.ConnectionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static kz.epam.iotservice.util.ConstantsForAttributes.CURRENT_USER_HOME_ID_PARAMETER;
import static kz.epam.iotservice.util.ConstantsForAttributes.DEVICE_MESSAGE_SESSION_STATEMENT;
import static kz.epam.iotservice.util.JspConstants.DEVICE_VIEW_JSP;
import static kz.epam.iotservice.util.JspConstants.UPDATE_DEVICE_JSP;
import static kz.epam.iotservice.util.OtherConstants.KEY_EMPTY;
import static kz.epam.iotservice.util.ServiceManagement.loadHomeIntoRequest;

public class DeviceViewService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ConnectionException {
        if (request.getSession().getAttribute(CURRENT_USER_HOME_ID_PARAMETER) != null) {
            loadHomeIntoRequest(request);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(DEVICE_VIEW_JSP);
            requestDispatcher.forward(request, response);
        } else {
            request.setAttribute(DEVICE_MESSAGE_SESSION_STATEMENT, KEY_EMPTY);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(UPDATE_DEVICE_JSP);
            requestDispatcher.forward(request, response);
        }
    }
}
