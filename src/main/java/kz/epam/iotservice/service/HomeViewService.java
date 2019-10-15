package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.DeviceDAO;
import kz.epam.iotservice.dao.FunctionDAO;
import kz.epam.iotservice.dao.HomeDAO;
import kz.epam.iotservice.database.ConnectionPool;
import kz.epam.iotservice.entity.Device;
import kz.epam.iotservice.entity.Home;
import kz.epam.iotservice.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.util.ConstantsForAttributes.HOME_ID_PARAMETER;
import static kz.epam.iotservice.util.ConstantsForAttributes.HOME_SESSION_STATEMENT;
import static kz.epam.iotservice.util.ConstantsUri.MAIN_URI;

public class HomeViewService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String SQL_PROBLEMS = "Cannot upload homeList in Home view service, some MySQL problems";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, SQLException, ConnectionException {
        HomeDAO homeDAO = new HomeDAO();
        Connection connection = ConnectionPool.getInstance().retrieve();
        try {
            if (request.getParameter(HOME_ID_PARAMETER) != null) {
                long homeID = Long.parseLong(request.getParameter(HOME_ID_PARAMETER));
                FunctionDAO functionDAO = new FunctionDAO();
                DeviceDAO deviceDAO = new DeviceDAO();
                Home home = homeDAO.getHomeByID(homeID, connection);
                home.setHomeInstalledDevices(deviceDAO.getDevicesList(home, connection));
                List<Device> homeInstalledDevices = new ArrayList<>();
                for (Device device : home.getHomeInstalledDevices()) {
                    device.setFunctions(functionDAO.getFunctionsList(device, connection));
                    homeInstalledDevices.add(device);
                }
                home.setHomeInstalledDevices(homeInstalledDevices);
                request.getSession().setAttribute(HOME_SESSION_STATEMENT, home);
            }
            connection.commit();
        } catch (SQLException e){
            LOGGER.error(SQL_PROBLEMS, e);
            connection.rollback();
        } finally {
            ConnectionPool.getInstance().putBack(connection);
        }
        response.sendRedirect(MAIN_URI);
    }
}
