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

public class AddNewDeviceService implements Service {

    private String deviceMessage;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)){
            createNewDevice(request, response);
        } else {
            DeviceTypeDAO deviceTypeDAO = new DeviceTypeDAO();
            List<DeviceType> deviceTypeList = deviceTypeDAO.getDeviceTypeList();
            request.getSession().setAttribute("deviceTypeList", deviceTypeList);
            deviceMessage = "Here you can add device into your home";
            newDeviceForward(request, response);
        }
    }

    private boolean isApplyPressed(HttpServletRequest request){
        if (request.getParameter("apply") != null){
            return true;
        } else {
            return false;
        }
    }

    private void newDeviceForward(HttpServletRequest request, HttpServletResponse response)
                                    throws  IOException, ServletException, SQLException, ConnectionException{
        request.getSession().setAttribute("deviceMessage", deviceMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/newDevice.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createNewDevice(HttpServletRequest request, HttpServletResponse response)
                                     throws  IOException, ServletException, SQLException, ConnectionException{
        FunctionDefinitionDAO functionDefinitionDAO = new FunctionDefinitionDAO();
        Long deviceTypeID = Long.parseLong(request.getParameter("deviceTypeID"));
        String deviceName = "";
        DeviceDAO deviceDAO = new DeviceDAO();
        Device device = new Device();
        FunctionDAO functionDAO = new FunctionDAO();
        Home home = (Home) request.getSession().getAttribute("home");
        try{
            deviceName = validateDeviceName(request.getParameter("deviceName"));
        } catch (ValidationException e){
            deviceMessage = "Please enter valid device name!";
            response.sendRedirect("/addNewDevice");
        }
        device.setDeviceName(deviceName);
        device.setDeviceDefinitionID(deviceTypeID);
        device.setDeviceHomePlacedID(home.getHomeID());
        device.setDeviceID(deviceDAO.addNewDevice(device, deviceTypeID, home));
        for (FunctionDefinition functionDefinition : functionDefinitionDAO.getFunctionDefinitionList(deviceTypeID)){
            Function function = new Function();
            function.setFunctionType(function.getFunctionType());
            functionDAO.addNewFunction(function, functionDefinition, device.getDeviceID());
        }
        deviceMessage = "Device added successful";
        response.sendRedirect("/addNewDevice");
    }
}

