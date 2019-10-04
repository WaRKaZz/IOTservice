package kz.epam.IOTservice.service;

import kz.epam.IOTservice.database.FunctionDAO;
import kz.epam.IOTservice.entity.Device;
import kz.epam.IOTservice.entity.Function;
import kz.epam.IOTservice.entity.Home;
import kz.epam.IOTservice.exception.ConnectionException;
import kz.epam.IOTservice.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static kz.epam.IOTservice.validation.FunctionValidation.*;

public class DeviceUpdateService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
        Home home = (Home) request.getSession().getAttribute("home");
        for (Device device: home.getHomeInstalledDevices()) {
            for (Function function: device.getFunctions()) {
                String requestValueOfParameter = request.getParameter(String.valueOf(function.getFunctionId()));
                if (requestValueOfParameter != null){
                    try {
                        switch (function.getFunctionType()){
                            case "BOOL":
                                function.setFunctionTrue(validatateFunctionTrue(requestValueOfParameter));
                                commitUpdate(function, response);
                                break;
                            case "INTEGER":
                                function.setFunctionInteger(validateFunctionInteger(requestValueOfParameter));
                                commitUpdate(function, response);
                                break;
                            case "TEXT":
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
            throws IOException, ServletException, SQLException, ConnectionException {
        FunctionDAO functionDAO = new FunctionDAO();
        functionDAO.updateFunctionData(function);
        response.sendRedirect("/devices");
    }
}
