package kz.epam.iotservice.dao;

import kz.epam.iotservice.entity.Home;
import kz.epam.iotservice.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.util.DatabaseConstants.*;

public class HomeDAO {
    private static final String GET_HOME_BY_ID_SQL = "SELECT * FROM  IOT_DATABASE.HOME WHERE HOME_ID = ?";
    private static final String UPDATE_HOME_SQL = "UPDATE IOT_DATABASE.HOME " +
            "SET HOME_NAME = ?, " +
            "HOME_ADDRESS = ?" +
            "WHERE HOME_ID = ?";
    private static final String ADD_NEW_HOME_SQL = "INSERT INTO IOT_DATABASE.HOME (HOME_NAME, HOME_ADDRESS) " +
            "VALUES (?, ?)";
    private static final String DELETE_HOME_IN_HOME_TABLE_SQL = "DELETE FROM IOT_DATABASE.HOME " +
            "WHERE HOME_ID = ?";
    private static final String DELETE_HOME_IN_USER_TO_HOME_TABLE = "DELETE FROM IOT_DATABASE.USER_TO_HOME " +
            "WHERE HOME_ID = ?";
    private static final String GET_HOME_LIST_BY_USER_AND_ROLE = "SELECT * FROM IOT_DATABASE.USER_TO_HOME LEFT JOIN " +
            "IOT_DATABASE.HOME " +
            "ON IOT_DATABASE.USER_TO_HOME.HOME_ID " +
            "= IOT_DATABASE.HOME.HOME_ID " +
            "WHERE USER_ID = ? AND USER_HOME_ROLE = ?";
    private static final String ADD_DEPENDENCY_TO_USER_TO_HOME_SQL = "INSERT INTO IOT_DATABASE.USER_TO_HOME " +
            "(USER_ID, HOME_ID, USER_HOME_ROLE) " +
            "VALUES (?, ?, ?)";
    private static final String GET_LAST_INSERTED_ID = "SELECT LAST_INSERT_ID()";

    public Home getHomeByID(long homeID, Connection connection) throws SQLException {
        Home home = new Home();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_HOME_BY_ID_SQL)) {
            preparedStatement.setLong(1, homeID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    home = configureHomeObject(resultSet);
                }
            }
        }
        return home;
    }


    public List<Home> getHomeListByRole(User user, int role, Connection connection) throws SQLException {
        List<Home> homeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_HOME_LIST_BY_USER_AND_ROLE)) {
            preparedStatement.setLong(1, user.getUserID());
            preparedStatement.setInt(2, role);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Home home = configureHomeObject(resultSet);
                    homeList.add(home);
                }
            }
        }
        return homeList;
    }

    public void deleteHome(Long homeID, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_HOME_IN_USER_TO_HOME_TABLE)) {
            preparedStatement.setLong(1, homeID);
            preparedStatement.executeUpdate();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_HOME_IN_HOME_TABLE_SQL)) {
            preparedStatement.setLong(1, homeID);
            preparedStatement.executeUpdate();
        }
    }

    public Long addNewHome(Home home, Connection connection) throws SQLException {
        long createdHomeID = (long) 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_HOME_SQL)) {
            configureHomeStatement(home, preparedStatement);
            preparedStatement.executeUpdate();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_INSERTED_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                createdHomeID = resultSet.getLong(1);
            }
        }
        return createdHomeID;
    }

    public void addHomeDependencyAdministrator(Long homeID, Long userID, Connection connection) throws SQLException {
        final int ADMIN_ROLE_IN_HOME = 1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_DEPENDENCY_TO_USER_TO_HOME_SQL)) {
            preparedStatement.setLong(1, userID);
            preparedStatement.setLong(2, homeID);
            preparedStatement.setInt(3, ADMIN_ROLE_IN_HOME);
            preparedStatement.executeUpdate();
        }
    }

    public void updateHome(Home home, Connection connection) throws SQLException {
        final int HOME_ID_POSITION = 3;
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HOME_SQL)) {
            configureHomeStatement(home, preparedStatement);
            preparedStatement.setLong(HOME_ID_POSITION, home.getHomeID());
            preparedStatement.executeUpdate();
        }
    }

    private Home configureHomeObject(ResultSet resultSet) throws SQLException {
        Home home = new Home();
        home.setHomeID(resultSet.getLong(HOME_ID));
        home.setHomeAddress(resultSet.getString(HOME_ADDRESS));
        home.setHomeName(resultSet.getString(HOME_NAME));
        return home;
    }

    private void configureHomeStatement(Home home, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, home.getHomeName());
        preparedStatement.setString(2, home.getHomeAddress());
    }
}
