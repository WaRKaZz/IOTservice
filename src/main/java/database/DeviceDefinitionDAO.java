package database;

import entity.DeviceDefinition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DeviceDefinitionDAO {
    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final static String GET_DEVICE_DEFINITIONS_LIST_SQL = "SELECT * FROM IOT_DATABASE.DEVICE_DEFINITIONS " +
            "ORDER BY DEVICE_DEFINITION_NAME";
    private final static String GET_DEVICE_DEFINITION_BY_ID_SQL = "SELECT * FROM IOT_DATABASE.DEVICE_DEFINITIONS " +
            "WHERE DEVICE_DEFINITION_ID = ?";
    private final static String ADD_NEW_DEVICE_DEFINITION = "UPDATE IOT_DATABASE.DEVICE_DEFINITIONS " +
            "SET DEVICE_DEFINITION_NAME = ?" +
            "WHERE DEVICE_DEFINITION_ID = ?";
    private final static String UPDATE_DEVICE_DEFINITION = "INSERT INTO IOT_DATABASE.DEVICE_DEFINITIONS " +
            "(DEVICE_DEFINITION_NAME) VALUE (?)";


    public List<DeviceDefinition> getDeviceDefinitionsList() throws SQLException {
        Connection connection = CONNECTION_POOL.retrieve();
        List<DeviceDefinition> deviceDefinitionList= new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DEVICE_DEFINITIONS_LIST_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                DeviceDefinition deviceDefinition = configureDefinitionObject(resultSet);
                deviceDefinitionList.add(deviceDefinition);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return deviceDefinitionList;
    }

    public DeviceDefinition getDeviceDefinitionByID(Long deviceDefinitionID) throws SQLException{
        Connection connection = CONNECTION_POOL.retrieve();
        DeviceDefinition deviceDefinition = new DeviceDefinition();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DEVICE_DEFINITION_BY_ID_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                deviceDefinition = configureDefinitionObject(resultSet);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return deviceDefinition;
    }

    public void addNewDeviceDefinition (DeviceDefinition deviceDefinition) throws SQLException{
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_DEVICE_DEFINITION)){
            configureDefinitionStatement(deviceDefinition, preparedStatement);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void updateDeviceDefinition(DeviceDefinition deviceDefinition) throws SQLException{
        final int DEVICE_DEFINITION_ID_POSITION = 2;
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DEVICE_DEFINITION)){
            configureDefinitionStatement(deviceDefinition, preparedStatement);
            preparedStatement.setLong(DEVICE_DEFINITION_ID_POSITION, deviceDefinition.getDeviceDefinitionID());
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    private DeviceDefinition configureDefinitionObject(ResultSet resultSet) throws SQLException{
        DeviceDefinition deviceDefinition = new DeviceDefinition();
        deviceDefinition.setDeviceDefinitionID(resultSet.getLong("DEVICE_DEFINITION_ID"));
        deviceDefinition.setDeviceName(resultSet.getString("DEVICE_DEFINITION_NAME"));
        return deviceDefinition;
    }

    private void configureDefinitionStatement (DeviceDefinition deviceDefinition, PreparedStatement preparedStatement) throws  SQLException{
        preparedStatement.setString(1, deviceDefinition.getDeviceName());
    }
}
