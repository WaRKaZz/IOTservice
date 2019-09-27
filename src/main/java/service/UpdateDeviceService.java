package service;

import database.DeviceDAO;
import database.DeviceTypeDAO;
import database.FunctionDAO;
import database.FunctionDefinitionDAO;
import entity.*;
import exception.ConnectionException;
import exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static validation.DeviceValidator.*;
import static util.HomeManagement.updateHome;

public class UpdateDeviceService implements Service {

    private String deviceMessage = "";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
                                                throws IOException, ServletException, SQLException, ConnectionException {

        if (isApplyPressed(request)){
            updateDevice(request, response);
        } else {
            updateHome(request);
            DeviceTypeDAO deviceTypeDAO = new DeviceTypeDAO();
            List<DeviceType> deviceTypeList = deviceTypeDAO.getDeviceTypeList();
            request.getSession().setAttribute("deviceTypeList", deviceTypeList);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/updateDevice.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private boolean isApplyPressed(HttpServletRequest request){
        return  request.getParameter("apply") != null;
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
            deviceTypeID = validateID(request.getParameter("deviceTypeID"));
            deviceID = validateID(request.getParameter("deviceID"));
            deviceName = validateDeviceName(request.getParameter("deviceName"));
            homeID = validateID(request.getParameter("homeID"));

        } catch (ValidationException e){
            deviceMessage = "Please, choose correct device parameters";
            validationException = true;
        }
        if (!validationException){
            device.setDeviceID(deviceID);
            device.setDeviceDefinitionID(deviceTypeID);
            device.setDeviceName(deviceName);
            device.setDeviceHomePlacedID(homeID);
            functionDAO.deleteFunctionsInDevice(device.getDeviceID());
            deviceDAO.deleteDeviceByID(device.getDeviceID());
            device.setDeviceID(deviceDAO.addNewDevice(device));
            for (FunctionDefinition functionDefinition : functionDefinitionDAO.getFunctionDefinitionList(deviceTypeID)){
                Function function = new Function();
                functionDAO.addNewFunction(function, functionDefinition, device.getDeviceID());
            }
            deviceMessage = "Updating device was correct!";
        }
        refreshPage(request, response);
    }

    private void refreshPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.getSession().setAttribute("deviceMessage", deviceMessage);
        response.sendRedirect("/updateDevice");
    }
}
