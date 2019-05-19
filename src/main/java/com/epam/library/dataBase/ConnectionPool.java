package com.epam.library.dataBase;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private String url;
    private String user;
    private String password;
    private String driverDB;
    private Properties properties = getProperties("connectionPool.properties");
    private final int maxConnection = Integer.parseInt(properties.getProperty("pool.maxConnection"));
    private static ConnectionPool instance = null;
    private BlockingQueue<Connection> freeConnections = new ArrayBlockingQueue<>(maxConnection);

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
        this.url = properties.getProperty("pool.url");
        this.password = properties.getProperty("pool.password");
        this.user = properties.getProperty("pool.user");
        this.driverDB = properties.getProperty("pool.driver");
    }

    private Properties getProperties(String configurationFile){
        Properties properties = new Properties();
        InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(configurationFile);
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
        Connection connection = null;
        try {
            connection = freeConnections.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private BlockingQueue<Connection> createConnections(){
        Connection connection;
        while(freeConnections.size() < maxConnection){
            try {
                connection = DriverManager.getConnection(url, user, password);
                freeConnections.put(connection);
            } catch (InterruptedException e) {
                log.warn(e);
                e.printStackTrace();
            } catch (SQLException e) {
                log.warn(e);
                e.printStackTrace();
            }
        }
        return freeConnections;
    }

    public synchronized void returnConnection(Connection connection){
        if ( (connection != null) && (freeConnections.size()<= maxConnection)) {
            try {
                freeConnections.put(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
