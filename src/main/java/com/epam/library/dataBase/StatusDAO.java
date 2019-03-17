package com.epam.library.dataBase;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusDAO {

    public static final String GET_STATE_NAME = "SELECT NAME FROM STATE WHERE ID_LANGUAGE=? AND NAME!='выдан' AND NAME!='issued'";
    public static final String GET_STATUS_ID_BY_NAME = "SELECT ID_STATE FROM STATE WHERE NAME=?";
    private static final Logger log = Logger.getLogger("BookGenreDAO");
    private ConnectionPool connectionPool;
    private Connection connection = null;

    public List<String> getStatuses(int idLanguage){
        List<String> statuses = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_STATE_NAME)){
            preparedStatement.setInt(1, idLanguage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                statuses.add(resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return statuses;
    }

    public int getStatusID(String name){
        int idStatus = 0;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_STATUS_ID_BY_NAME)){
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                idStatus = resultSet.getInt("ID_STATE");
            }

        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return idStatus;
    }
}
