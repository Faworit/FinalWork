package com.epam.library.dataBase.impl;

import com.epam.library.dataBase.ConnectionPool;
import com.epam.library.dataBase.UserDAO;
import com.epam.library.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.library.validator.AuthorizationValidator.validatePassword;

public class UserDAOImpl implements UserDAO {

    private static final String GET_USER_BY_MAIL_PASSWORD = "SELECT ID_USER, PASSWORD, NAME, SURNAME, MAIL, TELEPHONE, " +
            "BIRTH_DAY, BLOCK, ROLE_NAME FROM USER U, ROLE R WHERE MAIL = ? AND PASSWORD = ? AND U.ROLE=R.ID_ROLE";
    private static final String GET_USERS_BY_ID_ORDER = "SELECT U.ID_USER, U.PASSWORD, U.NAME, U.SURNAME, U.MAIL, " +
            "U.TELEPHONE, U.BIRTH_DAY, U.BLOCK, R.ROLE_NAME  FROM USER U, ORDER2USER O2U, BOOKING B, ROLE R " +
            "WHERE B.ID_ORDER = O2U.ID_ORDER AND U.ID_USER=O2U.ID_USER AND U.ROLE=R.ID_ROLE AND B.ID_ORDER=?";
    private static final String INSERT_PERFORMER_OF_ORDER = "INSERT INTO order2user (ID_ORDER, ID_USER) VALUES (?, ?)";
    private static final String CHANGE_PASSWORD = "UPDATE USER SET PASSWORD = ? WHERE (ID_USER = ?)";
    private static final String CHECK_USER_BY_MAIL_ID = "SELECT ID_USER, MAIL FROM USER WHERE MAIL=?";
    private static final String ADD_USER = "INSERT INTO USER (PASSWORD, NAME, SURNAME, MAIL, TELEPHONE, BIRTH_DAY, " +
            "BLOCK, ROLE) VALUES (?,?,?,?,?,?,?,?)";
    private ConnectionPool connectionPool;
    private Connection connection = null;

    @Override
    public void changePassword(String password, int ID) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, ID);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public User getUserByMailPassword(String mail, String password) throws SQLException {
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
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> getUserByID(int idOrder) throws SQLException {
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

        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    private User setParametrsToUser(User user, ResultSet resultSet) throws SQLException {
            user.setId(resultSet.getInt("ID_User"));
            user.setPassword(resultSet.getString("password"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surName"));
            user.setMail(resultSet.getString("mail"));
            user.setTelephone(resultSet.getString("telephone"));
            user.setBirthDay(resultSet.getDate("birth_day"));
            user.setBlock(resultSet.getString("block"));
            user.setRole(resultSet.getString("role_name"));
        return user;
    }

    @Override
    public void insertPerformer(int idOrder, int idLibrarian) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PERFORMER_OF_ORDER)){
            preparedStatement.setInt(1, idOrder);
            preparedStatement.setInt(2, idLibrarian);
            preparedStatement.executeUpdate();
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
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

    @Override
    public List getAll(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void create(Object object) throws SQLException {
        User user = (User) object;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getMail());
            preparedStatement.setString(5, user.getTelephone());
            java.sql.Date sqlStartDate = new java.sql.Date(user.getBirthDay().getTime());
            preparedStatement.setDate(6, sqlStartDate);
            preparedStatement.setString(7, user.getBlock());
            int idRole = getIdRole(user);
            preparedStatement.setInt(8, idRole);
            preparedStatement.executeUpdate();
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(int id){
        throw new UnsupportedOperationException();
    }

    private int getIdRole(User user){
        String role = user.getRole();
        int idRole = 0;
        if(role.equals("reader")){
            idRole = 2;
        } else{
            if(role.equals("librarian")){
                idRole = 3;
            }
        }
        return idRole;
    }
}
