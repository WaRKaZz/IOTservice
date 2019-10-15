package kz.epam.iotservice.controller;

import kz.epam.iotservice.exception.ConnectionException;
import kz.epam.iotservice.service.Service;
import kz.epam.iotservice.service.ServiceFactory;
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
    private static final String SERVICE_CANNOT_BE_EXECUTED = "Service Cannot be executed";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Service service = ServiceFactory.getService(request.getRequestURI());
        try {
            service.execute(request, response);
        } catch (SQLException | IOException | ConnectionException e) {
            LOGGER.error(SERVICE_CANNOT_BE_EXECUTED, e);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new ServletException(e);
        }
    }
}

