package kz.epam.IOTService.service;

import kz.epam.IOTService.database.DeviceDAO;
import kz.epam.IOTService.database.DeviceTypeDAO;
import kz.epam.IOTService.database.FunctionDAO;
import kz.epam.IOTService.database.FunctionDefinitionDAO;
import kz.epam.IOTService.entity.Device;
import kz.epam.IOTService.entity.DeviceType;
import kz.epam.IOTService.entity.Function;
import kz.epam.IOTService.entity.FunctionDefinition;
import kz.epam.IOTService.exception.ConnectionException;
import kz.epam.IOTService.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static kz.epam.IOTService.util.IOTServiceConstants.*;
import static kz.epam.IOTService.util.ServiceManagement.isApplyPressed;
import static kz.epam.IOTService.util.ServiceManagement.updateHomeInSession;
import static kz.epam.IOTService.validation.DeviceValidator.validateDeviceName;
import static kz.epam.IOTService.validation.DeviceValidator.validateID;

public class UpdateDeviceService implements Service {

    private static final String KEY_UPDATE_DEVICE_MESSAGE_INCORRECT_PARAMETERS = "key.updateDeviceMessageIncorrectParameters";
    private static final String KEY_UPDATE_DEVICE_MESSAGE_SUCCESS = "key.updateDeviceMessageSuccess";
    private String deviceMessage = KEY_EMPTY;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)){
            updateDevice(request, response);
        } else {
            if (request.getSession().getAttribute(HOME_SESSION_STATEMENT) != null){
                updateHomeInSession(request);
            }
            DeviceTypeDAO deviceTypeDAO = new DeviceTypeDAO();
            List<DeviceType> deviceTypeList = deviceTypeDAO.getDeviceTypeList();
            request.getSession().setAttribute(DEVICE_MESSAGE_SESSION_STATEMENT, deviceMessage);
            request.getSession().setAttribute(DEVICE_TYPE_LIST_SESSION_STATEMENT, deviceTypeList);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(UPDATE_DEVICE_JSP);
            requestDispatcher.forward(request, response);
        }
    }

    private void updateDevice(HttpServletRequest request, HttpServletResponse response)
            throws  SQLException, ConnectionException, IOException{
        DeviceDAO deviceDAO = new DeviceDAO();
        Device device = new Device();
        FunctionDefinitionDAO functionDefinitionDAO = new FunctionDefinitionDAO();
        FunctionDAO functionDAO = new FunctionDAO();
        Long deviceID = (long) 0, deviceTypeID = (long) 0, homeID = (long) 0;
        String deviceName = "";
        boolean validationException = false;
        try {
            if (!isDeleteDevicePressed(request)){
                deviceTypeID = validateID(request.getParameter(DEVICE_TYPE_ID_PARAMETER));
                deviceID = validateID(request.getParameter(DEVICE_ID_PARAMETER));
                deviceName = validateDeviceName(request.getParameter(DEVICE_NAME_PARAMETER));
                homeID = validateID(request.getParameter(HOME_ID_PARAMETER));
            }
        } catch (ValidationException e){
            deviceMessage = KEY_UPDATE_DEVICE_MESSAGE_INCORRECT_PARAMETERS;
            validationException = true;
        }
        if (!validationException){
            device.setDeviceID(deviceID);
            device.setDeviceDefinitionID(deviceTypeID);
            device.setDeviceName(deviceName);
            device.setDeviceHomePlacedID(homeID);
            functionDAO.deleteFunctionsInDevice(device.getDeviceID());
            deviceDAO.deleteDeviceByID(device.getDeviceID());
            if (!isDeleteDevicePressed(request)){
                device.setDeviceID(deviceDAO.addNewDevice(device));
                for (FunctionDefinition functionDefinition : functionDefinitionDAO.getFunctionDefinitionList(deviceTypeID)){
                    Function function = new Function();
                    functionDAO.addNewFunction(function, functionDefinition, device.getDeviceID());
                }
            }
            deviceMessage = KEY_UPDATE_DEVICE_MESSAGE_SUCCESS;
        }
        refreshPage(request, response);
    }

    private static boolean isDeleteDevicePressed(HttpServletRequest request){
        if (request.getParameter(DELETE_PARAMETER) != null){
            return  request.getParameter(DELETE_PARAMETER).equals(TRUE);
        } else {
            return false;
        }
    }

    private void refreshPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.getSession().setAttribute(DEVICE_MESSAGE_SESSION_STATEMENT, deviceMessage);
        response.sendRedirect(UPDATE_DEVICE_URI);
    }
}
