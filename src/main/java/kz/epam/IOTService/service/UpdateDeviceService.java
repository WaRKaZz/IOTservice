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

import static kz.epam.IOTService.util.ServiceManagement.isApplyPressed;
import static kz.epam.IOTService.util.ServiceManagement.updateHomeInSession;
import static kz.epam.IOTService.validation.DeviceValidator.validateDeviceName;
import static kz.epam.IOTService.validation.DeviceValidator.validateID;

public class UpdateDeviceService implements Service {

    private String deviceMessage = "key.empty";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)){
            updateDevice(request, response);
        } else {
            if (request.getSession().getAttribute("home") != null){
                updateHomeInSession(request);
            }
            DeviceTypeDAO deviceTypeDAO = new DeviceTypeDAO();
            List<DeviceType> deviceTypeList = deviceTypeDAO.getDeviceTypeList();
            request.getSession().setAttribute("deviceMessage", deviceMessage);
            request.getSession().setAttribute("deviceTypeList", deviceTypeList);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/updateDevice.jsp");
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
                deviceTypeID = validateID(request.getParameter("deviceTypeID"));
                deviceID = validateID(request.getParameter("deviceID"));
                deviceName = validateDeviceName(request.getParameter("deviceName"));
                homeID = validateID(request.getParameter("homeID"));
            }
        } catch (ValidationException e){
            deviceMessage = "key.updateDeviceMessageIncorrectParameters";
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
            deviceMessage = "key.updateDeviceMessageSuccess";
        }
        refreshPage(request, response);
    }

    private static boolean isDeleteDevicePressed(HttpServletRequest request){
        if (request.getParameter("delete") != null){
            return  request.getParameter("delete").equals("true");
        } else {
            return false;
        }
    }

    private void refreshPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.getSession().setAttribute("deviceMessage", deviceMessage);
        response.sendRedirect("/updateDevice");
    }
}
