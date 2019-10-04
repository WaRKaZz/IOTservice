package kz.epam.IOTService.service;

import kz.epam.IOTService.database.FunctionDAO;
import kz.epam.IOTService.entity.Device;
import kz.epam.IOTService.entity.Function;
import kz.epam.IOTService.entity.Home;
import kz.epam.IOTService.exception.ConnectionException;
import kz.epam.IOTService.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static kz.epam.IOTService.util.IOTServiceConstants.*;
import static kz.epam.IOTService.validation.FunctionValidation.*;

public class DeviceUpdateService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException,SQLException, ConnectionException {
        Home home = (Home) request.getSession().getAttribute(HOME_SESSION_STATEMENT);
        for (Device device: home.getHomeInstalledDevices()) {
            for (Function function: device.getFunctions()) {
                String requestValueOfParameter = request.getParameter(String.valueOf(function.getFunctionId()));
                if (requestValueOfParameter != null){
                    try {
                        switch (function.getFunctionType()){
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
                    } catch (ValidationException e){
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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
