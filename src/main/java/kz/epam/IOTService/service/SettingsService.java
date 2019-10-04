package kz.epam.IOTService.service;

import kz.epam.IOTService.exception.ConnectionException;
import kz.epam.IOTService.util.IOTServiceConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SettingsService implements Service{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ConnectionException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(IOTServiceConstants.SETTINGS_JSP);
        requestDispatcher.forward(request, response);
    }
}
