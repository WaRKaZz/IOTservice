package kz.epam.IOTService.database;

import kz.epam.IOTService.entity.FunctionDefinition;
import kz.epam.IOTService.exception.ConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.IOTService.util.IOTServiceConstants.*;

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

    private FunctionDefinition configureDefinitionObject(ResultSet resultSet) throws SQLException{
        FunctionDefinition functionType = new FunctionDefinition();
        functionType.setFunctionDefinitionID(resultSet.getLong(FUNCTION_DEFINITION_ID));
        functionType.setFunctionName(resultSet.getString(FUNCTION_NAME));
        functionType.setInput(resultSet.getBoolean(FUNCTION_TYPE));
        return functionType;
    }
}
