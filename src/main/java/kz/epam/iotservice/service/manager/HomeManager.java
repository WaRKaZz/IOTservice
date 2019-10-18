package kz.epam.iotservice.service.manager;

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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.constants.Attributes.CURRENT_USER_HOME_ID_PARAMETER;
import static kz.epam.iotservice.constants.Attributes.HOME_SESSION_STATEMENT;

public class HomeManager {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CANNOT_UPDATE_HOME_IN_SERVICE = "Cannot update home in service";

    public void loadHomeIntoRequest(HttpServletRequest request) throws SQLException, ConnectionException {
        Long homeID = (Long) request.getSession().getAttribute(CURRENT_USER_HOME_ID_PARAMETER);
        if (homeID != null) {
            Home home = configureByHomeID(homeID);
            request.setAttribute(HOME_SESSION_STATEMENT, home);
        }
    }

    public Home configureByHomeID(Long homeID) throws SQLException, ConnectionException {
        Home home = new Home();
        HomeDAO homeDAO = new HomeDAO();
        FunctionDAO functionDAO = new FunctionDAO();
        DeviceDAO deviceDAO = new DeviceDAO();
        Connection connection = ConnectionPool.getInstance().retrieve();
        List<Device> homeInstalledDevices = new ArrayList<>();
        try {
            home = homeDAO.getHomeByID(homeID, connection);
            home.setHomeInstalledDevices(deviceDAO.getDevicesList(home, connection));
            for (Device device : home.getHomeInstalledDevices()) {
                device.setFunctions(functionDAO.getFunctionsList(device, connection));
                homeInstalledDevices.add(device);
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_UPDATE_HOME_IN_SERVICE, e);
            connection.rollback();
        } finally {
            ConnectionPool.getInstance().putBack(connection);
        }

        home.setHomeInstalledDevices(homeInstalledDevices);
        if (home.getHomeInstalledDevices().isEmpty()) {
            home.setHomeInstalledDevices(null);
        }
        return home;
    }
}
