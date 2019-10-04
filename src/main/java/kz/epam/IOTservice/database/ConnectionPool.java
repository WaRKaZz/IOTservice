package kz.epam.IOTService.database;

import kz.epam.IOTService.exception.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionPool  {
    private static final ConnectionPool instance = new ConnectionPool();
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static final int CONNECTION_AMOUNT = 30;
    private final ConcurrentLinkedQueue<Connection> CONNECTION_QUEUE = new ConcurrentLinkedQueue<>();

    public static ConnectionPool getInstance(){
        return instance;
    }

    public ConnectionPool(){
        try{
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        for (int i = 0; i < CONNECTION_AMOUNT; i++){
            CONNECTION_QUEUE.add(getConnection());
        }
    }

    public Connection retrieve(){
        if (CONNECTION_QUEUE.isEmpty()) {
            CONNECTION_QUEUE.add(getConnection());
        }
        return CONNECTION_QUEUE.poll();
    }

    public void putBack(Connection connection) throws ConnectionException {
        if (connection != null) {
            if (CONNECTION_QUEUE.size() < CONNECTION_AMOUNT){
                CONNECTION_QUEUE.add(connection);
            }
        } else {
            throw new ConnectionException();
        }

    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e){
            System.out.println("ERROR MY DATABASE IS HURT");
        }
        return connection;
    }
}
