package kz.epam.iotservice.dao;

import kz.epam.iotservice.entity.Device;
import kz.epam.iotservice.entity.Home;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.util.DatabaseConstants.*;

public class DeviceDAO {
    private static final String ADD_NEW_DEVICE_SQL = "INSERT INTO IOT_DATABASE.DEVICE" +
            " (DEVICE_NAME, DEVICE_DEFINITION_TO_DEVICE, HOME_PLACED_ID)" +
            " VALUES (?, ?, ?)";
    private static final String DELETE_DEVICE_SQL = "DELETE FROM IOT_DATABASE.DEVICE WHERE DEVICE_ID = ?";
    private static final String GET_DEVICE_LIST_IN_HOME_SQL = "SELECT * FROM IOT_DATABASE.DEVICE " +
            "LEFT JOIN IOT_DATABASE.DEVICE_DEFINITIONS " +
            "ON IOT_DATABASE.DEVICE.DEVICE_DEFINITION_TO_DEVICE " +
            "= IOT_DATABASE.DEVICE_DEFINITIONS.DEVICE_DEFINITION_ID " +
            "WHERE HOME_PLACED_ID = ? " +
            "ORDER BY DEVICE_NAME";
    private static final String GET_LAST_INSERTED_ID = "SELECT LAST_INSERT_ID()";

    public List<Device> getDevicesList(Home home, Connection connection) throws SQLException {
        List<Device> devices = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DEVICE_LIST_IN_HOME_SQL)) {
            preparedStatement.setLong(1, home.getHomeID());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Device device = configureDeviceObject(resultSet);
                    devices.add(device);
                }
            }
        }
        return devices;
    }

    public Long addNewDevice(Device device, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_DEVICE_SQL)) {
            configureDeviceStatement(device, preparedStatement);
            preparedStatement.executeUpdate();
        }
        long lastDeviceID = Long.parseLong("0");
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_INSERTED_ID)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    lastDeviceID = resultSet.getLong(1);
                }
            }
        }
        return lastDeviceID;
    }

    public void deleteDeviceByID(Long deviceID, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DEVICE_SQL)) {
            preparedStatement.setLong(1, deviceID);
            preparedStatement.executeUpdate();
        }
    }

    private Device configureDeviceObject(ResultSet resultSet) throws SQLException {
        Device device = new Device();
        device.setDeviceName(resultSet.getString(DEVICE_NAME));
        device.setDeviceDefinitionName(resultSet.getString(DEVICE_DEFINITION_NAME));
        device.setDeviceID(resultSet.getLong(DEVICE_ID));
        device.setDeviceDefinitionID(resultSet.getLong(DEVICE_DEFINITION_TO_DEVICE));
        device.setDeviceHomePlacedID(resultSet.getLong(HOME_PLACED_ID));
        return device;
    }

    private void configureDeviceStatement(Device device, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, device.getDeviceName());
        preparedStatement.setLong(2, device.getDeviceDefinitionID());
        preparedStatement.setLong(3, device.getDeviceHomePlacedID());
    }
}
