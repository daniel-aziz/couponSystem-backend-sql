package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 * This class establish connection to the DB, via Singletone design
 */
public class ConnectionPool {

    // FIELDS
    private static ConnectionPool instance = null; // instance for this class
    private static final int NUM_OF_CONNECTIONS = 20; // number of connections to use - HARD-CODED
    private Stack<Connection> connections = new Stack<>(); // initialized the ConnectionPool Stack of connections

    // C'TOR

    /**
     * ctor will call openAllConnections(); if instance was not been made
     * @throws SQLException
     */
    private ConnectionPool() throws SQLException {
        openAllConnections();
    }


    // METHODS

    /**
     * this method will fill and the connection Stack with valid connections.
     * @throws SQLException
     */
    public void openAllConnections() throws SQLException {
        for (int index = 0; index < NUM_OF_CONNECTIONS; index += 1) {
            Connection connection = DriverManager.getConnection(DataBaseManager.URL, DataBaseManager.USER_NAME, DataBaseManager.PASSWORD);
            connections.push(connection);
        }
    }

    /**
     * gets the Class instance
     * @return One ConnectionPool instance
     * @throws SQLException
     */
    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    /**
     * returns synchronized Connection from the connection Stack
     * @return Connection
     * @throws InterruptedException
     */
    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            if (connections.isEmpty()) {
                connections.wait();
            }
            return connections.pop();
        }
    }

    /**
     * Receives a Connection instance, and push back a the instance to the connection Stack
     * @param connection
     */
    public void returnConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            connections.notify();
        }


    }

    /**
     * If all connection have been returned to the Stack, will remove all elements from the stack, so the connection to the DB is no longer valid.
     * @throws InterruptedException
     */
    public void closeAllConnections() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUM_OF_CONNECTIONS) {
                connections.wait();
            }
            connections.removeAllElements();
        }
    }
}
