package database;

import entity.Device;
import entity.DeviceDefinition;
import entity.Home;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceDAO {
    private ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final static String GET_DEVICE_BY_ID_SQL = "SELECT * FROM IOT_DATABASE.DEVICE " +
            "LEFT JOIN IOT_DATABASE.DEVICE_DEFINITIONS " +
            "ON IOT_DATABASE.DEVICE.DEVICE_DEFINITION_TO_DEVICE " +
            "= IOT_DATABASE.DEVICE_DEFINITIONS.DEVICE_DEFINITION_ID";
    private final static String ADD_NEW_DEVICE_SQL = "INSERT INTO IOT_DATABASE.DEVICE" +
            " (DEVICE_NAME, DEVICE_DEFINITION_TO_DEVICE, HOME_PLACED_ID)" +
            " VALUES (?, ?, ?)";
    private final static String UPDATE_DEVICE_SQL = "UPDATE IOT_DATABASE.DEVICE SET DEVICE_DEFINITION_TO_DEVICE = ?, " +
            "DEVICE_NAME = ?, " +
            "HOME_PLACED_ID = ?" +
            "WHERE DEVICE_ID = ?";
    private final static String GET_DEVICE_LIST_SQL = "SELECT * FROM IOT_DATABASE.DEVICE WHERE HOME_PLACED_ID = ?"
                                                               + " ORDER BY DEVICE_NAME";

    public Device getDeviceByID(Long deviceID)throws SQLException {
        Device device = new Device();
        FunctionDAO functionDAO = new FunctionDAO();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DEVICE_BY_ID_SQL)){
            preparedStatement.setLong(1, deviceID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                device = configureDeviceObject(resultSet);
                device.setFunctions(functionDAO.getFunctionsList(device));
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return device;
    }

    public List<Device> getDevicesList(Home home) throws SQLException{
        List<Device> devices = new ArrayList<>();
        FunctionDAO functionDAO = new FunctionDAO();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DEVICE_LIST_SQL)){
            preparedStatement.setLong(1, home.getHomeID());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Device device = configureDeviceObject(resultSet);
                device.setFunctions(functionDAO.getFunctionsList(device));
                devices.add(device);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return devices;
    }

    public void addNewDevice(Device device, DeviceDefinition deviceDefinition, Home home) throws SQLException{
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_DEVICE_SQL)){
            configureDeviceStatement(device.getDeviceName(), deviceDefinition.getDeviceDefinitionID(), home.getHomeID(), preparedStatement);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }

    }
    public void updateDevice(Device device, DeviceDefinition deviceDefinition, Home home) throws SQLException{
        Connection connection = CONNECTION_POOL.retrieve();
        final int DEVICE_ID_POSITION = 4;
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DEVICE_SQL)){
            configureDeviceStatement(device.getDeviceName(), deviceDefinition.getDeviceDefinitionID(), home.getHomeID(), preparedStatement);
            preparedStatement.setLong(DEVICE_ID_POSITION, device.getDeviceID());
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    private Device configureDeviceObject(ResultSet resultSet) throws SQLException{
        Device device = new Device();
        device.setDeviceName("DEVICE_NAME");
        device.setDeviceDefinitionName("DEVICE_DEFINITION_NAME");
        device.setDeviceID(resultSet.getLong("DEVICE_ID"));
        device.setDeviceDefinitionID(resultSet.getLong("DEVICE_DEFINITION_TO_DEVICE"));
        device.setDeviceHomePlacedID(resultSet.getLong("HOME_PLACED_ID"));
        return device;
    }

    private void configureDeviceStatement(String deviceName, Long definitionID, Long homeID, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setString(1, deviceName);
        preparedStatement.setLong(2, definitionID);
        preparedStatement.setLong(3, homeID);
    }
}
