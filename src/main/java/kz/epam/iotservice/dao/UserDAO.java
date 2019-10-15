package kz.epam.iotservice.dao;

import kz.epam.iotservice.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.util.DatabaseConstants.*;


public class UserDAO {
    private final static String GET_USER_BY_LOGIN_SQL = "SELECT * FROM  IOT_DATABASE.USER WHERE USER_LOGIN = ?";
    private final static String GET_ALL_USER_LIST = "SELECT * FROM IOT_DATABASE.USER WHERE USER_ROLE > 1";
    private final static String ADD_NEW_USER_SQL = "INSERT INTO IOT_DATABASE.USER " +
            "(USER_LOGIN, USER_ROLE, USER_PASSWORD, USER_BLOCKED) " +
            "VALUES (?, ?, ?, ?)";
    private final static String UPDATE_USER_SQL = "UPDATE IOT_DATABASE.USER SET USER_LOGIN = ?, " +
            "USER_ROLE = ?, " +
            "USER_PASSWORD = ?, " +
            "USER_BLOCKED = ? " +
            "WHERE USER_ID = ?";
    private final static String BLOCK_USER_BY_ID_SQL = "UPDATE IOT_DATABASE.USER SET USER_BLOCKED = true " +
            "WHERE USER_ID = ? AND USER_ROLE > 1";
    private final static String UNBLOCK_USER_BY_ID_SQL = "UPDATE IOT_DATABASE.USER SET USER_BLOCKED = false " +
            "WHERE USER_ID = ? AND USER_ROLE > 1";

    public List getUsersList(Connection connection) throws SQLException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USER_LIST)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = configureUserObject(resultSet);
                    users.add(user);
                }
            }
        }
        return users;
    }

    public User getUserByLogin(String userLogin, Connection connection) throws SQLException {
        User user = new User();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN_SQL)) {
            preparedStatement.setString(1, userLogin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = configureUserObject(resultSet);
                }
            }
        }
        return user;
    }

    public void blockUserByID(long userID, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(BLOCK_USER_BY_ID_SQL)) {
            preparedStatement.setLong(1, userID);
            preparedStatement.executeUpdate();
        }
    }

    public void unblockUserByID(long userID, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UNBLOCK_USER_BY_ID_SQL)) {
            preparedStatement.setLong(1, userID);
            preparedStatement.executeUpdate();
        }
    }

    public void addNewUser(User user, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_USER_SQL)) {
            configureUserStatement(user, preparedStatement);
            preparedStatement.executeUpdate();
        }
    }


    public void updateUser(User user, Connection connection) throws SQLException {
        final int USER_ID_POSITION = 5;
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)) {
            configureUserStatement(user, preparedStatement);
            preparedStatement.setLong(USER_ID_POSITION, user.getUserID());
            preparedStatement.executeUpdate();
        }
    }


    private User configureUserObject(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserID(resultSet.getLong(USER_ID));
        user.setUserLogin(resultSet.getString(USER_LOGIN));
        user.setUserRole(resultSet.getInt(USER_ROLE));
        user.setUserPassword(resultSet.getString(USER_PASSWORD));
        user.setUserBlocked(resultSet.getBoolean(USER_BLOCKED));
        return user;
    }

    private void configureUserStatement(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getUserLogin());
        preparedStatement.setInt(2, user.getUserRole());
        preparedStatement.setString(3, user.getUserPassword());
        preparedStatement.setBoolean(4, user.getUserBlocked());
    }

}