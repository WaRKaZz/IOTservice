package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool  {
    private static final ConnectionPool instance = new ConnectionPool();
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static final int CONNECTION_AMOUNT = 30;
    private BlockingQueue<Connection> connectionQueue = new ArrayBlockingQueue<>(CONNECTION_AMOUNT);

    public static ConnectionPool getInstance(){
        return instance;
    }

    public ConnectionPool(){
        try{
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        for (int i = 1; i < CONNECTION_AMOUNT; i++){
            connectionQueue.add(getConnection());
        }
    }

    public synchronized Connection retrieve(){
        Connection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connectionQueue.poll();
    }

    public void putBack(Connection connection) {
        if (connection != null) {
            try {
                connectionQueue.put(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("There is no Connection");
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
