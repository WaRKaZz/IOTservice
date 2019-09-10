package database;

import entity.Home;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeDAO {
    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final static String  GET_HOME_BY_ID_SQL  = "SELECT * FROM  IOT_DATABASE.HOME WHERE HOME_ID = ?";
    private final static String UPDATE_HOME_SQL = "UPDATE IOT_DATABASE.HOME " +
            "SET HOME_NAME = ?, " +
            "HOME_ADDRESS = ?" +
            "WHERE HOME_ID = ?";
    private final static String ADD_NEW_HOME_SQL = "INSERT INTO IOT_DATABASE.HOME (HOME_NAME, HOME_ADDRESS) " +
            "VALUES (?, ?)";


    public Home getHomeByID(long homeID) throws SQLException {
        Home home = new Home();
        DeviceDAO deviceDAO = new DeviceDAO();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_HOME_BY_ID_SQL)){
            preparedStatement.setLong(1, homeID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                home = configureHomeObject(resultSet);
                home.setHomeInstalledDevices(deviceDAO.getDevicesList(home));
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return home;
    }

    public void addNewHome(Home home) throws SQLException{
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_HOME_SQL)){
            configureHomeStatement(home, preparedStatement);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void updateHome(Home home) throws  SQLException{
        Connection connection = CONNECTION_POOL.retrieve();
        final int HOME_ID_POSTITION = 3;
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HOME_SQL)){
            configureHomeStatement(home, preparedStatement);
            preparedStatement.setLong(HOME_ID_POSTITION, home.getHomeID());
            preparedStatement.executeUpdate();
        }
    }

    private Home configureHomeObject(ResultSet resultSet) throws SQLException{
        Home home = new Home();
        home.setHomeID(resultSet.getLong("HOME_ID"));
        home.setHomeAddress("HOME_ADDRESS");
        home.setHomeName("HOME_NAME");
        return home;
    }

    private void configureHomeStatement(Home home, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setString(1, home.getHomeName());
        preparedStatement.setString(2, home.getHomeAddress());
    }
}
