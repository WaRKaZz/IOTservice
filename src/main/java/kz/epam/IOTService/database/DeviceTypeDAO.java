package kz.epam.IOTService.database;

import kz.epam.IOTService.entity.DeviceType;
import kz.epam.IOTService.exception.ConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.IOTService.util.IOTServiceConstants.*;


public class DeviceTypeDAO {
    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final static String GET_DEVICE_TYPE_LIST_SQL = "SELECT * FROM IOT_DATABASE.DEVICE_DEFINITIONS " +
            "ORDER BY DEVICE_DEFINITION_NAME";

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
        deviceType.setDeviceTypeID(resultSet.getLong(DEVICE_DEFINITION_ID));
        deviceType.setDeviceTypeName(resultSet.getString(DEVICE_DEFINITION_NAME));
        return deviceType;
    }
}
