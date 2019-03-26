package com.epam.library.validator;

import com.epam.library.dataBase.BookDAO;

import java.sql.SQLException;

public class BookValidator {

    public static boolean checkBook(String ISBN) throws SQLException {
        boolean isAvailable;
        BookDAO bookDAO = new BookDAO();
        isAvailable = bookDAO.checkBook(ISBN);
        return isAvailable;
    }
}
