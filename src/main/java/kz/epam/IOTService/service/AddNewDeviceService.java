package kz.epam.IOTService.service;

import kz.epam.IOTService.database.DeviceDAO;
import kz.epam.IOTService.database.DeviceTypeDAO;
import kz.epam.IOTService.database.FunctionDAO;
import kz.epam.IOTService.database.FunctionDefinitionDAO;
import kz.epam.IOTService.entity.*;
import kz.epam.IOTService.exception.ConnectionException;
import kz.epam.IOTService.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.IOTService.util.IOTServiceConstants.*;
import static kz.epam.IOTService.util.ServiceManagement.isApplyPressed;
import static kz.epam.IOTService.validation.DeviceValidator.validateDeviceName;

public class AddNewDeviceService implements Service {

    private static final String KEY_NEW_DEVICE_MESSAGE_NOT_ADMIN = "key.newDeviceMessageNotAdmin";
    private static final String KEY_NEW_DEVICE_MESSAGE_VALID_DEVICE_NAME = "key.newDeviceMessageValidDeviceName";
    private static final String KEY_NEW_DEVICE_MESSAGE_SUCCESS = "key.newDeviceMessageSuccess";
    private String deviceMessage = KEY_EMPTY;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException, SQLException, ConnectionException {

        if (isApplyPressed(request) && isHomeAdmin(request)) {
            createNewDevice(request, response);
        } else {
            DeviceTypeDAO deviceTypeDAO = new DeviceTypeDAO();
            List<DeviceType> deviceTypeList = deviceTypeDAO.getDeviceTypeList();
            request.getSession().setAttribute(DEVICE_MESSAGE_SESSION_STATEMENT, deviceMessage);
            request.getSession().setAttribute(DEVICE_TYPE_LIST_SESSION_STATEMENT, deviceTypeList);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(NEW_DEVICE_JSP);
            requestDispatcher.forward(request, response);
        }
    }

    private boolean isHomeAdmin(HttpServletRequest request) {
        List objectAdminList = (List) request.getSession().getAttribute(HOME_ADMIN_LIST_SESSION_STATEMENT);
        List<Home> homeAdminList = new ArrayList<>();
        boolean homeContains = false;
        for (Object o : objectAdminList) {
            homeAdminList.add((Home) o);
        }
        Home home = (Home) request.getSession().getAttribute(HOME_SESSION_STATEMENT);
        for (Home homeInList : homeAdminList) {
            if (homeInList.equals(home)) {
                homeContains = true;
            }
        }
        if (!homeContains) {
            deviceMessage = KEY_NEW_DEVICE_MESSAGE_NOT_ADMIN;
            request.getSession().setAttribute(DEVICE_MESSAGE_SESSION_STATEMENT, deviceMessage);
        }
        return homeContains;
    }

    private void refreshPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        request.getSession().setAttribute(DEVICE_MESSAGE_SESSION_STATEMENT, deviceMessage);
        response.sendRedirect(ADD_NEW_DEVICE_URI);
    }

    private void createNewDevice(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        FunctionDefinitionDAO functionDefinitionDAO = new FunctionDefinitionDAO();
        Long deviceTypeID = Long.parseLong(request.getParameter(DEVICE_TYPE_ID_PARAMETER));
        String deviceName = "";
        DeviceDAO deviceDAO = new DeviceDAO();
        boolean validationException = false;
        Device device = new Device();
        FunctionDAO functionDAO = new FunctionDAO();
        Home home = (Home) request.getSession().getAttribute(HOME_SESSION_STATEMENT);
        try {
            deviceName = validateDeviceName(request.getParameter(DEVICE_NAME_PARAMETER));
        } catch (ValidationException e) {
            deviceMessage = KEY_NEW_DEVICE_MESSAGE_VALID_DEVICE_NAME;
            validationException = true;
        }
        if (!validationException) {
            device.setDeviceName(deviceName);
            device.setDeviceDefinitionID(deviceTypeID);
            device.setDeviceHomePlacedID(home.getHomeID());
            device.setDeviceID(deviceDAO.addNewDevice(device));
            for (FunctionDefinition functionDefinition : functionDefinitionDAO.getFunctionDefinitionList(deviceTypeID)) {
                Function function = new Function();
                functionDAO.addNewFunction(function, functionDefinition, device.getDeviceID());
            }
            deviceMessage = KEY_NEW_DEVICE_MESSAGE_SUCCESS;
        }
        refreshPage(request, response);
    }
}
