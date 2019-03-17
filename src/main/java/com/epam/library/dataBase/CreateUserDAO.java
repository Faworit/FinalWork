package com.epam.library.dataBase;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class CreateUserDAO {

    public static final String ADD_USER = "INSERT INTO USER (PASSWORD, NAME, SURNAME, MAIL, TELEPHONE, BIRTH_DAY, BLOCK, ROLE) VALUES (?,?,?,?,?,?,?,?)";
    private static final Logger log = Logger.getLogger("UserDAO");
    private ConnectionPool connectionPool;
    private Connection connection = null;

    public void setUser(String password, String name, String surname, String mail, String phone, Date birthday,
                        String block, String role){
        int idRole = 0;
        if(role.equals("reader")){
            idRole = 2;
        } else{
            if(role.equals("librarian")){
                idRole = 3;
            }
        }
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, mail);
            preparedStatement.setString(5, phone);
            java.sql.Date sqlStartDate = new java.sql.Date(birthday.getTime());
            preparedStatement.setDate(6, sqlStartDate);
            preparedStatement.setString(7, block);
            preparedStatement.setInt(8, idRole);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }
}
