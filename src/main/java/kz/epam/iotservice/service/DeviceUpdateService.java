package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.FunctionDAO;
import kz.epam.iotservice.entity.Device;
import kz.epam.iotservice.entity.Function;
import kz.epam.iotservice.entity.Home;
import kz.epam.iotservice.exception.ConnectionException;
import kz.epam.iotservice.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static kz.epam.iotservice.util.ConstantsForAttributes.*;
import static kz.epam.iotservice.util.ConstantsUri.DEVICES_URI;
import static kz.epam.iotservice.validation.FunctionValidation.*;

public class DeviceUpdateService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        Home home = (Home) request.getSession().getAttribute(HOME_SESSION_STATEMENT);
        for (Device device : home.getHomeInstalledDevices()) {
            for (Function function : device.getFunctions()) {
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
                        LOGGER.error(e);
                        LOGGER.error("Can not Update device");
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        break;
                    }

                }
            }
        }

    }

    private void commitUpdate(Function function, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        FunctionDAO functionDAO = new FunctionDAO();
        functionDAO.updateFunctionData(function);
        response.sendRedirect(DEVICES_URI);
    }
}
