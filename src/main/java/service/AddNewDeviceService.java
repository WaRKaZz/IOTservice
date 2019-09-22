package service;
import exception.ConnectionException;
import exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static validation.DeviceValidator.*;

import database.DeviceDAO;
import entity.Device;

public class AddNewDeviceService implements Service {

    private String deviceMessage;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)){
            createNewHome(request, response);
        } else {
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

    private void createNewHome(HttpServletRequest request, HttpServletResponse response)
                                     throws  IOException, ServletException, SQLException, ConnectionException{
        DeviceDAO deviceDAO = new DeviceDAO();
        Device device = new Device();
        String deviceName = "";
        try{
            deviceName = validateDeviceName(request.getParameter("homeName"));
        } catch (ValidationException e){
            deviceMessage = "Please enter valid device name!";
            newDeviceForward(request, response);
        }
        device.setDeviceName(deviceName);
        deviceDAO
        homeDAO.addNewHome(home);
        homeMessage = "Home added successful";
        response.sendRedirect("/addNewHome");
    }
}

}
