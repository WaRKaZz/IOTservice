package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.FunctionDAO;
import kz.epam.iotservice.database.ConnectionPool;
import kz.epam.iotservice.entity.Device;
import kz.epam.iotservice.entity.Function;
import kz.epam.iotservice.entity.Home;
import kz.epam.iotservice.exception.ConnectionException;
import kz.epam.iotservice.exception.ValidationException;
import kz.epam.iotservice.service.manager.HomeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static kz.epam.iotservice.constants.Attributes.*;
import static kz.epam.iotservice.constants.Uri.DEVICES_URI;
import static kz.epam.iotservice.validation.FunctionValidation.*;

public class DeviceUpdateService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CANNOT_USE_DATABASE_IN_UPDATING_DEVICE_SERVICE = "Cannot use database in Updating device service";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        Long homeID = (Long) request.getSession().getAttribute(CURRENT_USER_HOME_ID_PARAMETER);
        HomeManager homeManager = new HomeManager();
        Home home = homeManager.configureByHomeID(homeID);
        for (Device device : home.getHomeInstalledDevices()) {
            for (Function function : device.getFunctions()) {
                functionComplectation(request, response, function);
            }
        }
    }

    private void functionComplectation(HttpServletRequest request, HttpServletResponse response, Function function) throws IOException, SQLException, ConnectionException {
        String requestValueOfParameter = request.getParameter(String.valueOf(function.getFunctionId()));
        if (requestValueOfParameter != null) {
            try {
                switch (function.getFunctionType()) {
                    case BOOL_PARAMETER:
                        function.setFunctionTrue(validateFunctionTrue(requestValueOfParameter));
                        commitUpdate(function, response);
                        break;
                    case INTEGER_PARAMETER:
                        function.setFunctionInteger(validateFunctionInteger(requestValueOfParameter));
                        commitUpdate(function, response);
                        break;
                    case TEXT_PARAMETER:
                        function.setFunctionText(validateFunctionString(requestValueOfParameter));
                        commitUpdate(function, response);
                        break;
                }
            } catch (ValidationException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    private void commitUpdate(Function function, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        FunctionDAO functionDAO = new FunctionDAO();
        Connection connection = ConnectionPool.getInstance().retrieve();
        try {
            functionDAO.updateFunctionData(function, connection);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_USE_DATABASE_IN_UPDATING_DEVICE_SERVICE, e);
            connection.rollback();
        } finally {
            ConnectionPool.getInstance().putBack(connection);
        }
        response.sendRedirect(DEVICES_URI);
    }
}
