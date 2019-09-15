package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
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
        return connectionQueue.poll();
    }

    public synchronized void putBack(Connection connection) {
        if (connection != null) {
            connectionQueue.add(connection);
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
