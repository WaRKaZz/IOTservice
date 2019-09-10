package database;

import entity.FunctionDefinition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FunctionDefinitionDAO {
    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final static String GET_FUNCTION_DEFINITIONS_LIST_SQL = "SELECT * FROM IOT_DATABASE.FUNCTION_DEFINITIONS " +
            "LEFT JOIN IOT_DATABASE.DEVICE_DEFINITIONS " +
            "ON IOT_DATABASE.FUNCTION_DEFINITIONS.FUNCTION_DEFINITION_ID " +
            "= IOT_DATABASE.DEVICE_DEFINITIONS.DEVICE_DEFINITION_ID " +
            "ORDER BY FUNCTION_NAME";
    private final static String GET_FUNCTION_DEFINITION_BY_ID_SQL = "SELECT * FROM IOT_DATABASE.FUNCTION_DEFINITIONS " +
            "WHERE FUNCTION_DEFINITION_ID = ?";
    private final static String UPDATE_FUNCTION_DEFINITION = "UPDATE IOT_DATABASE.FUNCTION_DEFINITIONS " +
            "SET FUNCTION_NAME = ?, " +
            "FUNCTION_TYPE = ?, " +
            "DEVICE_DEFINITION_ID = ? " +
            "WHERE FUNCTION_DEFINITION_ID = ?";
    private final static String ADD_NEW_FUNCTION_DEFINITION = "INSERT INTO IOT_DATABASE.FUNCTION_DEFINITIONS " +
            "(FUNCTION_NAME, FUNCTION_TYPE, DEVICE_DEFINITION_ID) VALUES (?, ?, ?)";

    public List<FunctionDefinition> getFunctionDefinitionList() throws SQLException {
        Connection connection = CONNECTION_POOL.retrieve();
        List<FunctionDefinition> functionDefinitionList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_FUNCTION_DEFINITIONS_LIST_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                FunctionDefinition functionDefinition = configureDefinitionObject(resultSet);
                functionDefinitionList.add(functionDefinition);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return functionDefinitionList;
    }

    public FunctionDefinition getFunctionDefinitionByID(Long functionDefinitionID) throws SQLException{
        Connection connection = CONNECTION_POOL.retrieve();
        FunctionDefinition functionDefinition = new FunctionDefinition();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_FUNCTION_DEFINITION_BY_ID_SQL)){
             ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()){
                 functionDefinition = configureDefinitionObject(resultSet);
             }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return functionDefinition;
    }

    public void addNewFunctionDefinition (FunctionDefinition functionDefinition) throws SQLException{
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_FUNCTION_DEFINITION)){
            configureDefinitionStatement(functionDefinition, preparedStatement);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void updateFunctionDefinition(FunctionDefinition functionDefinition) throws SQLException{
        final int FUNCTION_DEFINITION_ID_POSITION = 4;
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FUNCTION_DEFINITION)){
            configureDefinitionStatement(functionDefinition, preparedStatement);
            preparedStatement.setLong(FUNCTION_DEFINITION_ID_POSITION, functionDefinition.getFunctionDefinitionID());
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    private FunctionDefinition configureDefinitionObject(ResultSet resultSet) throws SQLException{
        FunctionDefinition functionDefinition = new FunctionDefinition();
        functionDefinition.setFunctionDefinitionID(resultSet.getLong("FUNCTION_DEFINITION_ID"));
        functionDefinition.setFunctionName(resultSet.getString("FUNCTION_NAME"));
        functionDefinition.setInput(resultSet.getBoolean("FUNCTION_TYPE"));
        functionDefinition.setFunctionDefinitionID(resultSet.getLong("DEVICE_DEFINITION_ID"));
        return functionDefinition;
    }

    private void configureDefinitionStatement (FunctionDefinition functionDefinition, PreparedStatement preparedStatement) throws  SQLException{
        preparedStatement.setString(1, functionDefinition.getFunctionName());
        preparedStatement.setBoolean(2, functionDefinition.isInput());
        preparedStatement.setLong(3, functionDefinition.getDeviceDefinitionID());
    }

}
