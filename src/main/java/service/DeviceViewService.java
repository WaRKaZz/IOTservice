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

        if (request.getSession().getAttribute("home") != null){
            updateHomeInSession(request);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/deviceView.jsp");
            requestDispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/updateDevice.jsp");
            requestDispatcher.forward(request, response);
        }

    }
}
