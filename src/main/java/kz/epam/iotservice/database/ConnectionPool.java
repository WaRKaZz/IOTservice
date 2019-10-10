package kz.epam.iotservice.database;

import kz.epam.iotservice.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;

import static kz.epam.iotservice.util.OtherConstants.EMPTY_STRING;

public class ConnectionPool  {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CONNECTION_PULL_BUNDLE = "connectionPull";
    private static final String CONNECTION_PULL_URL = "url";
    private static final String CONNECTION_PULL_USER = "user";
    private static final String CONNECTION_PULL_PASSWORD = "password";
    private static final String CONNECTION_PULL_DRIVER = "driver";
    private static final String CONNECTION_PULL_INIT_CONNECTION_COUNT = "initConnectionCount";
    private final Locale LOCALE = new Locale(EMPTY_STRING);
    private final ResourceBundle BUNDLE = ResourceBundle.getBundle(CONNECTION_PULL_BUNDLE, LOCALE);
    private final String URL = BUNDLE.getString(CONNECTION_PULL_URL);
    private final String USER = BUNDLE.getString(CONNECTION_PULL_USER);
    private final String PASSWORD = BUNDLE.getString(CONNECTION_PULL_PASSWORD);
    private final int CONNECTION_AMOUNT = Integer.parseInt(BUNDLE.getString(CONNECTION_PULL_INIT_CONNECTION_COUNT));
    private static final ConnectionPool INSTANCE = new ConnectionPool();
    private final ConcurrentLinkedQueue<Connection> CONNECTION_QUEUE = new ConcurrentLinkedQueue<>();

    public static ConnectionPool getInstance(){
        return INSTANCE;
    }

    public ConnectionPool(){
        try{
            Class.forName(BUNDLE.getString(CONNECTION_PULL_DRIVER));
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
            LOGGER.error(e);
        }
        return connection;
    }
}
