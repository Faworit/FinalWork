package com.epam.library.dataBase.impl;

import com.epam.library.dataBase.ConnectionPool;
import com.epam.library.dataBase.LanguageDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LanguageDAOImpl implements LanguageDAO {

    private static final String GET_ID_LANGUAGE = "SELECT ID_LANGUAGE FROM LANGUAGE WHERE LANGUAGE=?";
    private ConnectionPool connectionPool;
    private Connection connection = null;

    public int getIdLanguage(String language) throws SQLException {
        int idLanguage = 1;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_ID_LANGUAGE)){
            preparedStatement.setString(1, language);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                idLanguage = resultSet.getInt("ID_LANGUAGE");
            }
        }
        return idLanguage;
    }

    @Override
    public List getAll(int id){
        throw new UnsupportedOperationException();
    }

    @Override
    public void create(Object object){
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id){
        throw new UnsupportedOperationException();
    }
}
