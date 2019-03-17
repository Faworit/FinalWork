package com.epam.library.dataBase;

import org.apache.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class ConnectionPool {

    private static final Logger log = Logger.getLogger("ConnectionPool");
    private String url;
    private String user;
    private String password;
    private String driverDB;
    private int maxConnection;
    private static ConnectionPool instance = null;
    private static ArrayList<Connection> freeConnections = new ArrayList<>();

    private ConnectionPool() {
        init();
    }

    private void init(){
        setDataForConnection();
        loadDrivers();
        createConnections();
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private void setDataForConnection(){
        Properties properties = getProperties("connectionPool.properties");
        this.url = properties.getProperty("pool.url");
        this.password = properties.getProperty("pool.password");
        this.user = properties.getProperty("pool.user");
        this.driverDB = properties.getProperty("pool.driver");
        this.maxConnection = Integer.parseInt(properties.getProperty("pool.maxConnection"));
    }

    public static Properties getProperties(String connection){
        Properties properties = new Properties();
        InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(connection);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error(e);
        }
        return properties;
    }

    private void loadDrivers() {
        try {
            Driver driver = (Driver) Class.forName(driverDB).newInstance();
        } catch (InstantiationException e) {
            log.warn(e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            log.warn(e);

        } catch (ClassNotFoundException e) {
            log.warn(e);
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() {
        Connection connection;
        if (!freeConnections.isEmpty()) {
            connection = freeConnections.get(freeConnections.size() - 1);
            freeConnections.remove(connection);
        } else {
            connection = newConnection();
        }
        return connection;
    }

    private ArrayList<Connection> createConnections(){
        if(freeConnections.size()<maxConnection){
            newConnection();
        }
        return freeConnections;
    }

    private Connection newConnection() {
        Connection connection = null;
        try {
            if (user == null) {
                connection = DriverManager.
                        getConnection(url);
            } else {
                connection = DriverManager.
                        getConnection(url, user, password);
            }

        } catch (SQLException e) {
            log.warn(e);
            e.printStackTrace();
        }
        return connection;
    }

    public synchronized void returnConnection(Connection connection){
        if ( (connection != null) && (freeConnections.size()<= maxConnection)) {
            freeConnections.add(connection);
        }
    }

}
