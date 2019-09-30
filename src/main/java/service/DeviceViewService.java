package service;

import entity.Device;
import entity.Function;
import entity.Home;
import exception.ConnectionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import static util.ServiceManagement.updateHomeInSession;

public class DeviceViewService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ConnectionException {
        updateHomeInSession(request);
        Home home = (Home) request.getSession().getAttribute("home");
        for (Device device: home.getHomeInstalledDevices()) {
            for (Function function: device.getFunctions()) {
                System.out.println(function.getFunctionName() + " " + function.getFunctionType() + " Input");

            }
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/deviceView.jsp");
        requestDispatcher.forward(request, response);
    }
}
