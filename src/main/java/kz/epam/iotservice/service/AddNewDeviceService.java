package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.DeviceDAO;
import kz.epam.iotservice.dao.DeviceTypeDAO;
import kz.epam.iotservice.dao.FunctionDAO;
import kz.epam.iotservice.dao.FunctionDefinitionDAO;
import kz.epam.iotservice.database.ConnectionPool;
import kz.epam.iotservice.entity.*;
import kz.epam.iotservice.exception.ConnectionException;
import kz.epam.iotservice.exception.ValidationException;
import kz.epam.iotservice.service.manager.HomeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.constants.Attributes.*;
import static kz.epam.iotservice.constants.Uri.ADD_NEW_DEVICE_URI;
import static kz.epam.iotservice.constants.Jsp.NEW_DEVICE_JSP;
import static kz.epam.iotservice.constants.Other.EMPTY_STRING;
import static kz.epam.iotservice.constants.Other.KEY_EMPTY;
import static kz.epam.iotservice.util.ServiceManagement.*;
import static kz.epam.iotservice.validation.DeviceValidator.validateDeviceName;

public class AddNewDeviceService implements Service {

    private static final String KEY_NEW_DEVICE_MESSAGE_NOT_ADMIN = "key.newDeviceMessageNotAdmin";
    private static final String KEY_NEW_DEVICE_MESSAGE_VALID_DEVICE_NAME = "key.newDeviceMessageValidDeviceName";
    private static final String KEY_NEW_DEVICE_MESSAGE_SUCCESS = "key.newDeviceMessageSuccess";
    private static final String CANNOT_ADD_NEW_DEVICE_BY_MY_SQL = "Cannot add new device by MySQL";
    private static final String CANNOT_DOWNLOAD_DEVICE_TYPE_LIST_IN_ADD_NEW_DEVICE_SERVICE = "Cannot download device type list in add new device service";
    private static final Logger LOGGER = LogManager.getRootLogger();
    private final HomeManager homeManager = new HomeManager();
    private String deviceMessage = KEY_EMPTY;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException, SQLException, ConnectionException {

        if (isApplyPressed(request) && isHomeAdmin(request)) {
            createNewDevice(request, response);
        } else {
            displayPage(request, response);
        }
    }

    private void displayPage(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException, ServletException, IOException {
        DeviceTypeDAO deviceTypeDAO = new DeviceTypeDAO();
        Connection connection = ConnectionPool.getInstance().retrieve();
        List<DeviceType> deviceTypeList = new ArrayList<>();
        try {
            deviceTypeList = deviceTypeDAO.getDeviceTypeList(connection);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_DEVICE_TYPE_LIST_IN_ADD_NEW_DEVICE_SERVICE, e);
            connection.rollback();
        } finally {
            ConnectionPool.getInstance().putBack(connection);
        }
        homeManager.loadHomeIntoRequest(request);
        request.setAttribute(DEVICE_MESSAGE_SESSION_STATEMENT, deviceMessage);
        request.setAttribute(DEVICE_TYPE_LIST_SESSION_STATEMENT, deviceTypeList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(NEW_DEVICE_JSP);
        requestDispatcher.forward(request, response);
    }

    private boolean isHomeAdmin(HttpServletRequest request) throws SQLException, ConnectionException {
        List objectAdminList = (List) request.getSession().getAttribute(HOME_ADMIN_LIST_SESSION_STATEMENT);
        List<Home> homeAdminList = new ArrayList<>();
        boolean homeContains = false;
        for (Object o : objectAdminList) {
            homeAdminList.add((Home) o);
        }
        Long homeID = (Long) request.getSession().getAttribute(CURRENT_USER_HOME_ID_PARAMETER);
        Home home = homeManager.configureByHomeID(homeID);
        for (Home homeInList : homeAdminList) {
            if (homeInList.equals(home)) {
                homeContains = true;
                break;
            }
        }
        if (!homeContains) {
            deviceMessage = KEY_NEW_DEVICE_MESSAGE_NOT_ADMIN;
            request.setAttribute(DEVICE_MESSAGE_SESSION_STATEMENT, deviceMessage);
        }
        return homeContains;
    }

    private void refreshPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setAttribute(DEVICE_MESSAGE_SESSION_STATEMENT, deviceMessage);
        response.sendRedirect(ADD_NEW_DEVICE_URI);
    }

    private void createNewDevice(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        FunctionDefinitionDAO functionDefinitionDAO = new FunctionDefinitionDAO();
        Long deviceTypeID = Long.parseLong(request.getParameter(DEVICE_TYPE_ID_PARAMETER));
        String deviceName = EMPTY_STRING;
        DeviceDAO deviceDAO = new DeviceDAO();
        boolean validationException = false;
        Device device = new Device();
        FunctionDAO functionDAO = new FunctionDAO();
        Long homeID = (Long) request.getSession().getAttribute(CURRENT_USER_HOME_ID_PARAMETER);
        Home home = homeManager.configureByHomeID(homeID);
        Connection connection = ConnectionPool.getInstance().retrieve();
        try {
            deviceName = validateDeviceName(request.getParameter(DEVICE_NAME_PARAMETER));
        } catch (ValidationException e) {
            deviceMessage = KEY_NEW_DEVICE_MESSAGE_VALID_DEVICE_NAME;
            validationException = true;
        }
        try {
            if (!validationException) {
                device.setDeviceName(deviceName);
                device.setDeviceDefinitionID(deviceTypeID);
                device.setDeviceHomePlacedID(home.getHomeID());
                device.setDeviceID(deviceDAO.addNewDevice(device, connection));
                for (FunctionDefinition functionDefinition : functionDefinitionDAO.getFunctionDefinitionList(deviceTypeID, connection)) {
                    Function function = new Function();
                    functionDAO.addNewFunction(function, functionDefinition, device.getDeviceID(), connection);
                }
                deviceMessage = KEY_NEW_DEVICE_MESSAGE_SUCCESS;
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_ADD_NEW_DEVICE_BY_MY_SQL, e);
            connection.rollback();
        } finally {
            ConnectionPool.getInstance().putBack(connection);
        }
        refreshPage(request, response);
    }
}

