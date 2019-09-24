package database;

import entity.FunctionDefinition;
import exception.ConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FunctionDefinitionDAO {
    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final static String GET_FUNCTION_DEFINITIONS_LIST_SQL = "SELECT * FROM IOT_DATABASE.DEFINITIONS_MANY_TO_MANY " +
            "LEFT JOIN IOT_DATABASE.FUNCTION_DEFINITIONS " +
            "ON IOT_DATABASE.DEFINITIONS_MANY_TO_MANY.M_FUNCTION_DEFINITION_ID " +
            "= IOT_DATABASE.FUNCTION_DEFINITIONS.FUNCTION_DEFINITION_ID " +
            "LEFT JOIN IOT_DATABASE.DEVICE_DEFINITIONS " +
            "ON IOT_DATABASE.DEFINITIONS_MANY_TO_MANY.M_DEVICE_DEFINITION_ID " +
            "= IOT_DATABASE.DEVICE_DEFINITIONS.DEVICE_DEFINITION_ID " +
            "WHERE M_DEVICE_DEFINITION_ID = ? " +
            "ORDER BY DEVICE_DEFINITION_NAME ";
    private final static String GET_FUNCTION_DEFINITION_BY_ID_SQL = "SELECT * FROM IOT_DATABASE.FUNCTION_DEFINITIONS " +
            "WHERE FUNCTION_DEFINITION_ID = ?";
    private final static String UPDATE_FUNCTION_DEFINITION = "UPDATE IOT_DATABASE.FUNCTION_DEFINITIONS " +
            "SET FUNCTION_NAME = ?, " +
            "FUNCTION_TYPE = ? " +
            "WHERE FUNCTION_DEFINITION_ID = ?";
    private final static String ADD_NEW_FUNCTION_DEFINITION = "INSERT INTO IOT_DATABASE.FUNCTION_DEFINITIONS " +
            "(FUNCTION_NAME, FUNCTION_TYPE) VALUES (?, ?)";

    public List<FunctionDefinition> getFunctionDefinitionList(Long deviceTypeID) throws SQLException, ConnectionException {
        Connection connection = CONNECTION_POOL.retrieve();
        List<FunctionDefinition> functionTypeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_FUNCTION_DEFINITIONS_LIST_SQL)){
            preparedStatement.setLong(1, deviceTypeID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                FunctionDefinition functionType = configureDefinitionObject(resultSet);
                functionTypeList.add(functionType);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return functionTypeList;
    }

    public FunctionDefinition getFunctionDefinitionByID(Long functionDefinitionID) throws SQLException, ConnectionException{
        Connection connection = CONNECTION_POOL.retrieve();
        FunctionDefinition functionType = new FunctionDefinition();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_FUNCTION_DEFINITION_BY_ID_SQL)){
             ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()){
                 functionType = configureDefinitionObject(resultSet);
             }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return functionType;
    }

    public void addNewFunctionDefinition (FunctionDefinition functionType) throws SQLException, ConnectionException{
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_FUNCTION_DEFINITION)){
            configureDefinitionStatement(functionType, preparedStatement);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void updateFunctionDefinition(FunctionDefinition functionType) throws SQLException, ConnectionException{
        final int FUNCTION_DEFINITION_ID_POSITION = 4;
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FUNCTION_DEFINITION)){
            configureDefinitionStatement(functionType, preparedStatement);
            preparedStatement.setLong(FUNCTION_DEFINITION_ID_POSITION, functionType.getFunctionDefinitionID());
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    private FunctionDefinition configureDefinitionObject(ResultSet resultSet) throws SQLException{
        FunctionDefinition functionType = new FunctionDefinition();
        functionType.setFunctionDefinitionID(resultSet.getLong("FUNCTION_DEFINITION_ID"));
        functionType.setFunctionName(resultSet.getString("FUNCTION_NAME"));
        functionType.setInput(resultSet.getBoolean("FUNCTION_TYPE"));
        return functionType;
    }

    private void configureDefinitionStatement (FunctionDefinition functionType,
                                               PreparedStatement preparedStatement) throws  SQLException{
        preparedStatement.setString(1, functionType.getFunctionName());
        preparedStatement.setBoolean(2, functionType.isInput());
        preparedStatement.setLong(3, functionType.getDeviceTypeID());
    }

}
