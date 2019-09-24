package service;
import database.*;
import entity.*;
import exception.ConnectionException;
import exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static validation.DeviceValidator.*;

public class AddNewDeviceService implements Service {

    private String deviceMessage;


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException, SQLException, ConnectionException {
        deviceMessage = "";

        if (isApplyPressed(request)&&isHomeAdmin(request)){
            createNewDevice(request, response);
        } else {
            DeviceTypeDAO deviceTypeDAO = new DeviceTypeDAO();
            List<DeviceType> deviceTypeList = deviceTypeDAO.getDeviceTypeList();
            request.getSession().setAttribute("deviceTypeList", deviceTypeList);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/newDevice.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private boolean isHomeAdmin(HttpServletRequest request) throws SQLException, ConnectionException {
        List objectAdminList = (List) request.getSession().getAttribute("homeAdminList");
        List<Home> homeAdminList = new ArrayList<>();
        boolean homeContains = false;
        for (Object o : objectAdminList) {
            homeAdminList.add((Home) o);
        }
        Home home = (Home) request.getSession().getAttribute("home");
        for (Home homeInList : homeAdminList) {
            if (homeInList.equals(home)) {
                homeContains = true;
            }
        }
        if (!homeContains){
            deviceMessage = "You are not admin in this house";
            request.getSession().setAttribute("deviceMessage", deviceMessage);
        }
        return homeContains;
    }

    private boolean isApplyPressed(HttpServletRequest request){
        if (request.getParameter("apply") != null){
            return true;
        } else {
            return false;
        }
    }

    private void refreshPage(HttpServletRequest request, HttpServletResponse response)
                                    throws  IOException, ServletException, SQLException, ConnectionException{
        request.getSession().setAttribute("deviceMessage", deviceMessage);
        response.sendRedirect("/addNewDevice");
    }

    private void createNewDevice(HttpServletRequest request, HttpServletResponse response)
                                     throws  IOException, ServletException, SQLException, ConnectionException{
        FunctionDefinitionDAO functionDefinitionDAO = new FunctionDefinitionDAO();
        Long deviceTypeID = Long.parseLong(request.getParameter("deviceTypeID"));
        String deviceName = "";
        DeviceDAO deviceDAO = new DeviceDAO();
        boolean validationException = false;
        Device device = new Device();
        FunctionDAO functionDAO = new FunctionDAO();
        Home home = (Home) request.getSession().getAttribute("home");
        try{
            deviceName = validateDeviceName(request.getParameter("deviceName"));
        } catch (ValidationException e){
            deviceMessage = "Please enter valid device name!";
            validationException = true;
        }
        if (!validationException){
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
        }
        refreshPage(request, response);
    }
}

