package database;

import entity.Device;
import entity.Function;
import entity.FunctionDefinition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionDAO {

    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final static String GET_FUNCTION_BY_ID_SQL = "SELECT * FROM IOT_DATABASE.FUNCTIONS " +
            "LEFT JOIN IOT_DATABASE.FUNCTION_DEFINITIONS " +
            "ON IOT_DATABASE.FUNCTIONS.FUNCTION_DEFINITION_ID " +
            "= IOT_DATABASE.FUNCTION_DEFINITIONS.FUNCTION_DEFINITION_ID" +
            " WHERE FUNCTION_ID = ?";
    private final static String ADD_NEW_FUNCTION_SQL = "INSERT INTO IOT_DATABASE.FUNCTIONS " +
            "(F_BOOL, F_INT, F_STRING, FUNCTION_DEFINITION_ID, FUNCTION_DEVICE_ID) " +
            "VALUES (?, ?, ?, ?, ?)";
    private final static String UPDATE_FUNCTION_SQL = "UPDATE IOT_DATABASE.FUNCTIONS SET F_BOOL = ?, " +
            "F_INT = ?, " +
            "F_STRING = ?, " +
            "FUNCTION_DEFINITION_ID = ?, " +
            "FUNCTION_DEVICE_ID = ? " +
            "WHERE FUNCTION_ID = ?";
    private final static String GET_STRING_FUNCTIONS_LIST_SQL = "SELECT * FROM IOT_DATABASE.FUNCTIONS " +
            "LEFT JOIN IOT_DATABASE.FUNCTION_DEFINITIONS " +
            "ON IOT_DATABASE.FUNCTIONS.FUNCTION_DEFINITION_ID " +
            "= IOT_DATABASE.FUNCTION_DEFINITIONS.FUNCTION_DEFINITION_ID " +
            "WHERE FUNCTION_DEVICE_ID = ? " +
            "ORDER BY FUNCTION_NAME ";

    public Function getFunctionByID(long functionID) throws SQLException {
        Function function = new Function();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_FUNCTION_BY_ID_SQL)){
            preparedStatement.setLong(1, functionID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                function = configureFunctionObject(resultSet);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return function;
    }

    public void addNewFunction (Function function, FunctionDefinition functionDefinition, Device device) throws SQLException{
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_FUNCTION_SQL)){
            configureFunctionStatement(function, functionDefinition.getFunctionDefinitionID(), device.getDeviceID(), preparedStatement);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void updateFunction (Function function, FunctionDefinition functionDefinition, Device device) throws SQLException{
        final int FUNCTION_ID_POSITION = 6;
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FUNCTION_SQL)){
            configureFunctionStatement(function, functionDefinition.getFunctionDefinitionID(), device.getDeviceID(), preparedStatement);
            preparedStatement.setLong(FUNCTION_ID_POSITION, function.getFunctionId());
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public List<Function> getFunctionsList(Device device)throws SQLException{
        List<Function> functions = new ArrayList<>();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_STRING_FUNCTIONS_LIST_SQL)){
            preparedStatement.setLong(1, device.getDeviceID());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Function function = configureFunctionObject(resultSet);
                functions.add(function);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return functions;
    }

    private Function configureFunctionObject(ResultSet resultSet) throws SQLException{
        Function function = new Function();
        function.setFunctionId(resultSet.getLong("FUNCTION_ID"));
        function.setFunctionName(resultSet.getString("FUNCTION_NAME"));
        function.setFunctionInput(resultSet.getBoolean("FUNCTION_TYPE"));
        function.setFunctionTrue(resultSet.getBoolean("F_BOOL"));
        function.setFunctionInteger(resultSet.getInt("F_INT"));
        function.setFunctionText(resultSet.getString("F_TEXT"));

        return function;
    }

    private void configureFunctionStatement(Function function, Long definitionID, Long deviceID,  PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setBoolean(1, function.isFunctionInput());
        preparedStatement.setInt(2, function.getFunctionInteger());
        preparedStatement.setString(3, function.getFunctionText());
        preparedStatement.setLong (4, definitionID);
        preparedStatement.setLong(5, deviceID);
    }
}
