package com.epam.library.dataBase;

import com.epam.library.entity.User;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.library.validator.AuthorizationValidator.validatePassword;

public class UserDAO {

    private static final Logger log = Logger.getLogger("UserDAO");
    public static final String GET_USER_BY_MAIL_PASSWORD = "SELECT ID_USER, PASSWORD, NAME, SURNAME, MAIL, TELEPHONE, BIRTH_DAY, BLOCK, ROLE_NAME FROM USER U, ROLE R WHERE MAIL=? AND PASSWORD=? AND U.ROLE=R.ID_ROLE";
    public static final String GET_USERS_BY_ID_ORDER = "SELECT U.ID_USER, U.PASSWORD, U.NAME, U.SURNAME, U.MAIL, U.TELEPHONE, U.BIRTH_DAY, U.BLOCK, R.ROLE_NAME  FROM USER U, ORDER2USER O2U, BOOKING B, ROLE R WHERE B.ID_ORDER = O2U.ID_ORDER AND U.ID_USER=O2U.ID_USER AND U.ROLE=R.ID_ROLE AND B.ID_ORDER=?";
    public  static final String INSERT_PERFORMER_OF_ORDER = "INSERT INTO order2user (ID_ORDER, ID_USER) VALUES (?, ?)";
    private ConnectionPool connectionPool;
    private Connection connection = null;

    public User getUserByMailPassword(String mail, String password){
        User user = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_MAIL_PASSWORD)){
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String passFromDB = resultSet.getString("password");
                if(validatePassword(password, passFromDB)) {
                    user = new User();
                    user = setParametrsToUser(user, resultSet);
                }
            }
        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    public List<User> getUserByID(int idOrder){
        List<User> users = new ArrayList<>();
        User user;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_BY_ID_ORDER)){
            preparedStatement.setInt(1, idOrder);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                user = new User();
                user = setParametrsToUser(user, resultSet);
                users.add(user);
            }

        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    private User setParametrsToUser(User user, ResultSet resultSet){
        try {
            user.setIDUser(resultSet.getInt("ID_User"));
            user.setPassword(resultSet.getString("password"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surName"));
            user.setMail(resultSet.getString("mail"));
            user.setTelephone(resultSet.getString("telephone"));
            user.setBirthDay(resultSet.getDate("birth_day"));
            user.setBlock(resultSet.getString("block"));
            user.setRole(resultSet.getString("role_name"));
        } catch (SQLException e) {
            log.error(e);
        }
        return user;
    }

    public void insertPerformer(int idOrder, int idLibrarian){
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PERFORMER_OF_ORDER)){
            preparedStatement.setInt(1, idOrder);
            preparedStatement.setInt(2, idLibrarian);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }
}
