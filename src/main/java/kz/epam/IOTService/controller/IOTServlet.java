package kz.epam.IOTService.controller;

import kz.epam.IOTService.exception.ConnectionException;
import kz.epam.IOTService.service.Service;
import kz.epam.IOTService.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class IOTServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Service service = ServiceFactory.getService(request.getRequestURI());
        try {
            service.execute(request,response);
        } catch (SQLException|IOException|ConnectionException e) {
            LOGGER.error(e);
        } catch (Exception e){
            LOGGER.error(e);
            throw new ServletException(e);
        }
    }
}

