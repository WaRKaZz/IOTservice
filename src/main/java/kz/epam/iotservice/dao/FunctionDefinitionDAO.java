package kz.epam.iotservice.dao;

import kz.epam.iotservice.entity.FunctionDefinition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.constants.Database.*;

public class FunctionDefinitionDAO {
    private static final String GET_FUNCTION_DEFINITIONS_LIST_SQL = "SELECT * FROM IOT_DATABASE.DEFINITIONS_MANY_TO_MANY " +
            "LEFT JOIN IOT_DATABASE.FUNCTION_DEFINITIONS " +
            "ON IOT_DATABASE.DEFINITIONS_MANY_TO_MANY.M_FUNCTION_DEFINITION_ID " +
            "= IOT_DATABASE.FUNCTION_DEFINITIONS.FUNCTION_DEFINITION_ID " +
            "LEFT JOIN IOT_DATABASE.DEVICE_DEFINITIONS " +
            "ON IOT_DATABASE.DEFINITIONS_MANY_TO_MANY.M_DEVICE_DEFINITION_ID " +
            "= IOT_DATABASE.DEVICE_DEFINITIONS.DEVICE_DEFINITION_ID " +
            "WHERE M_DEVICE_DEFINITION_ID = ? " +
            "ORDER BY DEVICE_DEFINITION_NAME ";

    public List<FunctionDefinition> getFunctionDefinitionList(Long deviceTypeID, Connection connection) throws SQLException {
        List<FunctionDefinition> functionTypeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_FUNCTION_DEFINITIONS_LIST_SQL)) {
            preparedStatement.setLong(1, deviceTypeID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    FunctionDefinition functionType = configureDefinitionObject(resultSet);
                    functionTypeList.add(functionType);
                }
            }
        }
        return functionTypeList;
    }

    private FunctionDefinition configureDefinitionObject(ResultSet resultSet) throws SQLException {
        FunctionDefinition functionType = new FunctionDefinition();
        functionType.setFunctionDefinitionID(resultSet.getLong(FUNCTION_DEFINITION_ID));
        functionType.setFunctionName(resultSet.getString(FUNCTION_NAME));
        functionType.setInput(resultSet.getBoolean(FUNCTION_TYPE));
        return functionType;
    }
}
