package database;

import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final static String GET_USER_BY_LOGIN_SQL = "SELECT * FROM  IOT_DATABASE.USER WHERE USER_LOGIN = ?";
    private final static String GET_USER_BY_ID_SQL = "SELECT * FROM  IOT_DATABASE.USER WHERE USER_ID = ?";
    private final static String ADD_NEW_USER_SQL = "INSERT INTO IOT_DATABASE.USER" +
            " (USER_LOGIN, USER_ROLE, USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_IMAGE_URL)" +
            " VALUES (?, ?, ?, ?, ?, ?)";
    private final static String UPDATE_USER_SQL = "UPDATE IOT_DATABASE.USER SET USER_LOGIN = ?," +
            " USER_ROLE = ?," +
            " USER_PASSWORD = ?," +
            " USER_FIRST_NAME = ?," +
            " USER_LAST_NAME = ?," +
            " USER_IMAGE_URL = ?" +
            "WHERE USER_ID = ?";

    public User getUserByLogin(String userLogin) throws SQLException {
        User user = new User();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN_SQL)){
            preparedStatement.setString(1,userLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user = configureUserObject(resultSet);
            }

        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return user;
    }

    public User getUserByID(long userId) throws SQLException {
        User user = new User();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID_SQL)){
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user = configureUserObject(resultSet);
            }

        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return user;
    }

    public void addNewUser(User user) throws SQLException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_USER_SQL)){
            configureUserStatement(user, preparedStatement);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void updateUser(User user) throws SQLException {
        final int USER_ID_POSITION = 7;
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)){
            configureUserStatement(user, preparedStatement);
            preparedStatement.setLong(USER_ID_POSITION, user.getUserID());
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }


    private User configureUserObject(ResultSet resultSet) throws SQLException{
        User user = new User();
        user.setUserID(resultSet.getLong("USER_ID"));
        user.setUserLogin(resultSet.getString("USER_LOGIN"));
        user.setUserRole(resultSet.getInt("USER_ROLE"));
        user.setUserPassword(resultSet.getString("USER_PASSWORD"));
        user.setUserFirstName(resultSet.getString("USER_FIRST_NAME"));
        user.setUserLastName(resultSet.getString("USER_LAST_NAME"));
        user.setUserImageUrl(resultSet.getString("USER_IMAGE_URL"));
        return user;
    }

    private void configureUserStatement(User user, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setString(1, user.getUserLogin());
        preparedStatement.setInt(2, user.getUserRole());
        preparedStatement.setString(3, user.getUserPassword());
        preparedStatement.setString(4, user.getUserFirstName());
        preparedStatement.setString(5, user.getUserLastName());
        preparedStatement.setString(6, user.getUserImageUrl());
    }

}