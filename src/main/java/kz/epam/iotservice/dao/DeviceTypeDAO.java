package kz.epam.iotservice.dao;

import kz.epam.iotservice.entity.DeviceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.constants.Database.DEVICE_DEFINITION_ID;
import static kz.epam.iotservice.constants.Database.DEVICE_DEFINITION_NAME;


public class DeviceTypeDAO {
    private static final String GET_DEVICE_TYPE_LIST_SQL = "SELECT * FROM IOT_DATABASE.DEVICE_DEFINITIONS " +
            "ORDER BY DEVICE_DEFINITION_NAME";

    public List<DeviceType> getDeviceTypeList(Connection connection) throws SQLException {
        List<DeviceType> deviceTypeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DEVICE_TYPE_LIST_SQL)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    DeviceType deviceType = configureTypeObject(resultSet);
                    deviceTypeList.add(deviceType);
                }
            }
        }
        return deviceTypeList;
    }

    private DeviceType configureTypeObject(ResultSet resultSet) throws SQLException {
        DeviceType deviceType = new DeviceType();
        deviceType.setDeviceTypeID(resultSet.getLong(DEVICE_DEFINITION_ID));
        deviceType.setDeviceTypeName(resultSet.getString(DEVICE_DEFINITION_NAME));
        return deviceType;
    }
}
