package kz.epam.iotservice.dao;

import kz.epam.iotservice.entity.Device;
import kz.epam.iotservice.entity.Function;
import kz.epam.iotservice.entity.FunctionDefinition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.constants.Database.*;

public class FunctionDAO {

    private static final String ADD_NEW_FUNCTION_SQL = "INSERT INTO IOT_DATABASE.FUNCTIONS " +
            "(F_BOOL, F_INT, F_STRING, FUNCTION_DEFINITION_ID, FUNCTION_DEVICE_ID) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String GET_STRING_FUNCTIONS_LIST_SQL = "SELECT * FROM IOT_DATABASE.FUNCTIONS " +
            "LEFT JOIN IOT_DATABASE.FUNCTION_DEFINITIONS " +
            "ON IOT_DATABASE.FUNCTIONS.FUNCTION_DEFINITION_ID " +
            "= IOT_DATABASE.FUNCTION_DEFINITIONS.FUNCTION_DEFINITION_ID " +
            "WHERE FUNCTION_DEVICE_ID = ? " +
            "ORDER BY FUNCTION_NAME ";
    private static final String DELETE_FUNCTIONS_LIST_SQL = "DELETE FROM IOT_DATABASE.FUNCTIONS " +
            "WHERE FUNCTION_DEVICE_ID = ?";
    private static final String UPDATE_FUNCTION_DATA_SQL = "UPDATE IOT_DATABASE.FUNCTIONS " +
            "SET F_BOOL = ?, " +
            "F_INT = ?, " +
            "F_STRING = ? " +
            "WHERE FUNCTION_ID = ?";

    public void addNewFunction(Function function, FunctionDefinition functionDefinition,
                               Long deviceID, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_FUNCTION_SQL)) {
            configureFunctionStatement(function, functionDefinition.getFunctionDefinitionID(), deviceID, preparedStatement);
            preparedStatement.executeUpdate();
        }
    }

    public void updateFunctionData(Function function, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FUNCTION_DATA_SQL)) {
            preparedStatement.setBoolean(1, function.getFunctionTrue());
            preparedStatement.setInt(2, function.getFunctionInteger());
            preparedStatement.setString(3, function.getFunctionText());
            preparedStatement.setLong(4, function.getFunctionId());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteFunctionsInDevice(Long deviceID, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FUNCTIONS_LIST_SQL)) {
            preparedStatement.setLong(1, deviceID);
            preparedStatement.executeUpdate();
        }
    }

    public List<Function> getFunctionsList(Device device, Connection connection) throws SQLException {
        List<Function> functions = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_STRING_FUNCTIONS_LIST_SQL)) {
            preparedStatement.setLong(1, device.getDeviceID());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Function function = configureFunctionObject(resultSet);
                    functions.add(function);
                }
            }
        }
        return functions;
    }

    private Function configureFunctionObject(ResultSet resultSet) throws SQLException {
        Function function = new Function();
        function.setFunctionId(resultSet.getLong(FUNCTION_ID));
        function.setFunctionName(resultSet.getString(FUNCTION_NAME));
        function.setFunctionInput(resultSet.getBoolean(FUNCTION_TYPE));
        function.setFunctionTrue(resultSet.getBoolean(F_BOOL));
        function.setFunctionInteger(resultSet.getInt(F_INT));
        function.setFunctionText(resultSet.getString(F_STRING));
        function.setFunctionType(resultSet.getString(FUNCTION_DATATYPE));

        return function;
    }

    private void configureFunctionStatement(Function function, Long definitionID, Long deviceID, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setBoolean(1, function.getFunctionTrue());
        preparedStatement.setInt(2, function.getFunctionInteger());
        preparedStatement.setString(3, function.getFunctionText());
        preparedStatement.setLong(4, definitionID);
        preparedStatement.setLong(5, deviceID);
    }
}
