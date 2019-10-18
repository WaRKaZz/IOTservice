package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.DeviceDAO;
import kz.epam.iotservice.dao.DeviceTypeDAO;
import kz.epam.iotservice.dao.FunctionDAO;
import kz.epam.iotservice.dao.FunctionDefinitionDAO;
import kz.epam.iotservice.database.ConnectionPool;
import kz.epam.iotservice.entity.Device;
import kz.epam.iotservice.entity.DeviceType;
import kz.epam.iotservice.entity.Function;
import kz.epam.iotservice.entity.FunctionDefinition;
import kz.epam.iotservice.exception.ConnectionException;
import kz.epam.iotservice.exception.ValidationException;
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

import static kz.epam.iotservice.util.ConstantsForAttributes.*;
import static kz.epam.iotservice.util.ConstantsUri.UPDATE_DEVICE_URI;
import static kz.epam.iotservice.util.JspConstants.UPDATE_DEVICE_JSP;
import static kz.epam.iotservice.util.OtherConstants.KEY_EMPTY;
import static kz.epam.iotservice.util.OtherConstants.TRUE;
import static kz.epam.iotservice.util.ServiceManagement.isApplyPressed;
import static kz.epam.iotservice.util.ServiceManagement.loadHomeIntoRequest;
import static kz.epam.iotservice.validation.DeviceValidator.validateDeviceName;
import static kz.epam.iotservice.validation.DeviceValidator.validateID;

public class UpdateDeviceService implements Service {

    private static final String KEY_UPDATE_DEVICE_MESSAGE_INCORRECT_PARAMETERS = "key.updateDeviceMessageIncorrectParameters";
    private static final String KEY_UPDATE_DEVICE_MESSAGE_SUCCESS = "key.updateDeviceMessageSuccess";
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CANNOT_WORK_WITH_DEVICES_AND_FUNCTIONS_IN_MY_SQL = "Cannot work with devices and functions in MySQL";
    private String deviceMessage = KEY_EMPTY;

    private static boolean isDeleteDeviceNotPressed(HttpServletRequest request) {
        if (request.getParameter(DELETE_PARAMETER) != null) {
            return !request.getParameter(DELETE_PARAMETER).equals(TRUE);
        } else {
            return true;
        }
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)) {
            updateDevice(request, response);
        } else {
            loadHomeIntoRequest(request);
            DeviceTypeDAO deviceTypeDAO = new DeviceTypeDAO();
            Connection connection = ConnectionPool.getInstance().retrieve();
            List<DeviceType> deviceTypeList = new ArrayList<>();
            try {
                deviceTypeList = deviceTypeDAO.getDeviceTypeList(connection);
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error(CANNOT_WORK_WITH_DEVICES_AND_FUNCTIONS_IN_MY_SQL, e);
                connection.rollback();
            } finally {
                ConnectionPool.getInstance().putBack(connection);
            }
            request.setAttribute(DEVICE_MESSAGE_SESSION_STATEMENT, deviceMessage);
            request.setAttribute(DEVICE_TYPE_LIST_SESSION_STATEMENT, deviceTypeList);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(UPDATE_DEVICE_JSP);
            requestDispatcher.forward(request, response);
        }
    }

    private void updateDevice(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ConnectionException, IOException {
        DeviceDAO deviceDAO = new DeviceDAO();
        Device device = new Device();
        FunctionDefinitionDAO functionDefinitionDAO = new FunctionDefinitionDAO();
        FunctionDAO functionDAO = new FunctionDAO();
        Long deviceID = (long) 0;
        Long deviceTypeID = (long) 0;
        Long homeID = (long) 0;
        String deviceName = "";
        Connection connection = ConnectionPool.getInstance().retrieve();
        boolean validationException = false;
        try {
            deviceTypeID = validateID(request.getParameter(DEVICE_TYPE_ID_PARAMETER));
            deviceID = validateID(request.getParameter(DEVICE_ID_PARAMETER));
            homeID = validateID(request.getParameter(HOME_ID_PARAMETER));
            if (isDeleteDeviceNotPressed(request)) {
                deviceName = validateDeviceName(request.getParameter(DEVICE_NAME_PARAMETER));
            }
        } catch (ValidationException e) {
            deviceMessage = KEY_UPDATE_DEVICE_MESSAGE_INCORRECT_PARAMETERS;
            validationException = true;
        }
        if (!validationException) {
            device.setDeviceID(deviceID);
            device.setDeviceDefinitionID(deviceTypeID);
            device.setDeviceName(deviceName);
            device.setDeviceHomePlacedID(homeID);
            try {
                functionDAO.deleteFunctionsInDevice(device.getDeviceID(), connection);
                deviceDAO.deleteDeviceByID(device.getDeviceID(), connection);
                if (isDeleteDeviceNotPressed(request)) {
                    device.setDeviceID(deviceDAO.addNewDevice(device, connection));
                    for (FunctionDefinition functionDefinition : functionDefinitionDAO.getFunctionDefinitionList(deviceTypeID, connection)) {
                        Function function = new Function();
                        functionDAO.addNewFunction(function, functionDefinition, device.getDeviceID(), connection);
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error(CANNOT_WORK_WITH_DEVICES_AND_FUNCTIONS_IN_MY_SQL, e);
                connection.rollback();
            } finally {
                ConnectionPool.getInstance().putBack(connection);
            }
            deviceMessage = KEY_UPDATE_DEVICE_MESSAGE_SUCCESS;
        }
        refreshPage(request, response);
    }

    private void refreshPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute(DEVICE_MESSAGE_SESSION_STATEMENT, deviceMessage);
        response.sendRedirect(UPDATE_DEVICE_URI);
    }
}
