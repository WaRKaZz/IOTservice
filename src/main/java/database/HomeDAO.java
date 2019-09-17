package database;

import entity.Home;
import entity.User;
import exception.ConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeDAO {
    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private final static String  GET_HOME_BY_ID_SQL  = "SELECT * FROM  IOT_DATABASE.HOME WHERE HOME_ID = ?";
    private final static String UPDATE_HOME_SQL = "UPDATE IOT_DATABASE.HOME " +
            "SET HOME_NAME = ?, " +
            "HOME_ADDRESS = ?" +
            "WHERE HOME_ID = ?";
    private final static String ADD_NEW_HOME_SQL = "INSERT INTO IOT_DATABASE.HOME (HOME_NAME, HOME_ADDRESS) " +
            "VALUES (?, ?)";
    private final static String GET_HOME_LIST_BY_USER_AND_ROLE = "SELECT * FROM IOT_DATABASE.USER_TO_HOME LEFT JOIN " +
            "IOT_DATABASE.HOME " +
            "ON IOT_DATABASE.USER_TO_HOME.HOME_ID " +
            "= IOT_DATABASE.HOME.HOME_ID " +
            "WHERE USER_ID = ? AND USER_HOME_ROLE = ?";
    private final static String GET_HOME_BY_HOME_AND_USERID = ""

    public Home getHomeByID(long homeID) throws SQLException, ConnectionException {
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


    public List<Home> getHomeListByRole (User user, int role) throws SQLException, ConnectionException {
        List<Home> homeList = new ArrayList<>();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_HOME_LIST_BY_USER_AND_ROLE)){
            preparedStatement.setLong(1, user.getUserID());
            preparedStatement.setInt(2, role);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Home home = configureHomeObject(resultSet);
                homeList.add(home);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return homeList;
    }

    public void addNewHome(Home home) throws SQLException, ConnectionException{
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_HOME_SQL)){
            configureHomeStatement(home, preparedStatement);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void updateHome(Home home) throws  SQLException, ConnectionException{
        Connection connection = CONNECTION_POOL.retrieve();
        final int HOME_ID_POSTITION = 3;
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HOME_SQL)){
            configureHomeStatement(home, preparedStatement);
            preparedStatement.setLong(HOME_ID_POSTITION, home.getHomeID());
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    private Home configureHomeObject(ResultSet resultSet) throws SQLException{
        Home home = new Home();
        home.setHomeID(resultSet.getLong("HOME_ID"));
        home.setHomeAddress(resultSet.getString("HOME_ADDRESS"));
        home.setHomeName(resultSet.getString("HOME_NAME"));
        return home;
    }

    private void configureHomeStatement(Home home, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setString(1, home.getHomeName());
        preparedStatement.setString(2, home.getHomeAddress());
    }
}
