package database;

import entity.DeviceType;
import exception.ConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DeviceTypeDAO {
    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final static String GET_DEVICE_TYPE_LIST_SQL = "SELECT * FROM IOT_DATABASE.DEVICE_DEFINITIONS " +
            "ORDER BY DEVICE_DEFINITION_NAME";
    private final static String GET_DEVICE_TYPE_BY_ID_SQL = "SELECT * FROM IOT_DATABASE.DEVICE_DEFINITIONS " +
            "WHERE DEVICE_DEFINITION_ID = ?";
    private final static String ADD_NEW_DEVICE_TYPE = "UPDATE IOT_DATABASE.DEVICE_DEFINITIONS " +
            "SET DEVICE_DEFINITION_NAME = ?" +
            "WHERE DEVICE_DEFINITION_ID = ?";
    private final static String UPDATE_DEVICE_TYPE = "INSERT INTO IOT_DATABASE.DEVICE_DEFINITIONS " +
            "(DEVICE_DEFINITION_NAME) VALUES (?)";
    private final static String ADD_NEW_CONNECTION_TO_DEVICE = "INSERT INTO IOT_DATABASE.DEFINITIONS_MANY_TO_MANY " +
            "(M_FUNCTION_DEFINITION_ID, M_DEVICE_DEFINITION_ID) VALUES (?, ?)";


    public List<DeviceType> getDeviceTypeList() throws SQLException, ConnectionException {
        Connection connection = CONNECTION_POOL.retrieve();
        List<DeviceType> deviceTypeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DEVICE_TYPE_LIST_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                DeviceType deviceType = configureTypeObject(resultSet);
                deviceTypeList.add(deviceType);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return deviceTypeList;
    }

    private DeviceType configureTypeObject(ResultSet resultSet) throws SQLException{
        DeviceType deviceType = new DeviceType();
        deviceType.setDeviceTypeID(resultSet.getLong("DEVICE_DEFINITION_ID"));
        deviceType.setDeviceTypeName(resultSet.getString("DEVICE_DEFINITION_NAME"));
        return deviceType;
    }
}
