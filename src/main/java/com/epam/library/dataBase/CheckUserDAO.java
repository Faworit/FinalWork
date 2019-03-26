package com.epam.library.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckUserDAO {

    public static final String CHECK_USER_BY_MAIL_ID = "SELECT ID_USER, MAIL FROM USER WHERE MAIL=?";
    private ConnectionPool connectionPool;
    private Connection connection = null;

    public boolean checkUser(String mail) throws SQLException {
        boolean isUser = false;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USER_BY_MAIL_ID)){
            preparedStatement.setString(1, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                isUser = true;
            }
        }
        return isUser;
    }
}
