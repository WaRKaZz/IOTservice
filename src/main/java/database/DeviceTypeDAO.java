package database;

import entity.DeviceType;
import entity.FunctionType;
import exception.ConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DeviceTypeDAO {
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
    private final static String ADD_NEW_CONNECTION_TO_DEVICE = "INSERT INTO IOT_DATABASE.DEFINITIONS_MANY_TO_MANY " +
            "(M_FUNCTION_DEFINITION_ID, M_DEVICE_DEFINITION_ID) values (?, ?)";


    public List<DeviceType> getDeviceDefinitionsList() throws SQLException, ConnectionException {
        Connection connection = CONNECTION_POOL.retrieve();
        List<DeviceType> deviceTypeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DEVICE_DEFINITIONS_LIST_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                DeviceType deviceType = configureDefinitionObject(resultSet);
                deviceTypeList.add(deviceType);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return deviceTypeList;
    }

    public DeviceType getDeviceDefinitionByID(Long deviceDefinitionID) throws SQLException, ConnectionException{
        Connection connection = CONNECTION_POOL.retrieve();
        DeviceType deviceType = new DeviceType();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DEVICE_DEFINITION_BY_ID_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                deviceType = configureDefinitionObject(resultSet);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return deviceType;
    }

    public void addNewDeviceDefinition (DeviceType deviceType) throws SQLException, ConnectionException{
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_DEVICE_DEFINITION)){
            configureDefinitionStatement(deviceType, preparedStatement);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void setAddNewConnectionToDevice (DeviceType deviceType,
                                             FunctionType functionType) throws SQLException, ConnectionException{
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_CONNECTION_TO_DEVICE)){
            preparedStatement.setLong(1, deviceType.getDeviceTypeID());
            preparedStatement.setLong(2, functionType.getFunctionTypeID());
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void updateDeviceDefinition(DeviceType deviceType) throws SQLException, ConnectionException{
        final int DEVICE_DEFINITION_ID_POSITION = 2;
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DEVICE_DEFINITION)){
            configureDefinitionStatement(deviceType, preparedStatement);
            preparedStatement.setLong(DEVICE_DEFINITION_ID_POSITION, deviceType.getDeviceTypeID());
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    private DeviceType configureDefinitionObject(ResultSet resultSet) throws SQLException{
        DeviceType deviceType = new DeviceType();
        deviceType.setDeviceTypeID(resultSet.getLong("DEVICE_DEFINITION_ID"));
        deviceType.setDeviceTypeName(resultSet.getString("DEVICE_DEFINITION_NAME"));
        return deviceType;
    }

    private void configureDefinitionStatement (DeviceType deviceType, PreparedStatement preparedStatement) throws  SQLException{
        preparedStatement.setString(1, deviceType.getDeviceTypeName());
    }
}
