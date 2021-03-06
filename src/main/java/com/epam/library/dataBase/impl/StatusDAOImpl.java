package com.epam.library.dataBase.impl;

import com.epam.library.dataBase.CommonDAO;
import com.epam.library.dataBase.ConnectionPool;
import com.epam.library.entity.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusDAOImpl implements CommonDAO {

    private static final String GET_STATE_NAME = "SELECT NAME FROM STATE WHERE ID_LANGUAGE=? AND NAME!='выдан' AND NAME!='issued'";
    private static final String GET_STATUS_ID_BY_NAME = "SELECT ID_STATE FROM STATE WHERE NAME=?";
    private ConnectionPool connectionPool;
    private Connection connection = null;

    public List<String> getStatuses(int idLanguage) throws SQLException {
        List<String> statuses = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_STATE_NAME)){
            preparedStatement.setInt(1, idLanguage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                statuses.add(resultSet.getString("NAME"));
            }
        }
        return statuses;
    }

    public int getStatusID(String name) throws SQLException {
        int idStatus = 0;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_STATUS_ID_BY_NAME)){
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                idStatus = resultSet.getInt("ID_STATE");
            }

        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return idStatus;
    }

    @Override
    public List getAll(int id){
        throw new UnsupportedOperationException();
    }

    @Override
    public void create(Entity object) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id){
        throw new UnsupportedOperationException();
    }
}
