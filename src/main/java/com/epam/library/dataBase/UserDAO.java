package com.epam.library.dataBase;

import com.epam.library.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends CommonDAO<User> {

    void changePassword(String password, int ID) throws SQLException;
    User getUserByMailPassword(String mail, String password) throws SQLException;
    List<User> getUserByID(int idOrder) throws SQLException;
    void insertPerformer(int idOrder, int idLibrarian) throws SQLException;
    boolean checkUser(String mail) throws SQLException;
}