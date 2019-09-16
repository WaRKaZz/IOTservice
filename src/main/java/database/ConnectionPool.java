package database;

import exception.ConnectionException;

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
    private ConcurrentLinkedQueue<Connection> connectionQueue = new ConcurrentLinkedQueue<>();

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
            connectionQueue.add(getConnection());
        }
    }

    public synchronized Connection retrieve(){
        if (connectionQueue.isEmpty()) {
            connectionQueue.add(getConnection());
        }
        return connectionQueue.poll();
    }

    public synchronized void putBack(Connection connection) throws ConnectionException {
        if (connection != null) {
            if (connectionQueue.size() < CONNECTION_AMOUNT){
                connectionQueue.add(connection);
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
