package com.epam.library.validator;

import com.epam.library.dataBase.impl.BookDAOImpl;

import java.sql.SQLException;

public class BookValidator {

    public static boolean checkBook(String ISBN) throws SQLException {
        BookDAOImpl bookDAO = new BookDAOImpl();
        return bookDAO.checkBook(ISBN);
    }
}
