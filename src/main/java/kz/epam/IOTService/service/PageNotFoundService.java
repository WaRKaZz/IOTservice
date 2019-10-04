package kz.epam.IOTService.service;

import kz.epam.IOTService.exception.ConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class PageNotFoundService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException{
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
