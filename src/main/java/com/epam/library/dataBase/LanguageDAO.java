package com.epam.library.dataBase;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguageDAO {

    public static final String GET_ID_LANGUAGE = "SELECT ID_LANGUAGE FROM LANGUAGE WHERE LANGUAGE=?";
    private static final Logger log = Logger.getLogger("UserDAO");
    private ConnectionPool connectionPool;
    private Connection connection = null;

    public int getIdLanguage(String language){
        int idLanguage = 1;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_ID_LANGUAGE)){
            preparedStatement.setString(1, language);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                idLanguage = resultSet.getInt("ID_LANGUAGE");
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return idLanguage;
    }
}
