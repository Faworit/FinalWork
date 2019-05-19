package com.epam.library.validator;

import com.epam.library.dataBase.BookDAO;

import java.sql.SQLException;

public class BookValidator {

    public static boolean checkBook(String ISBN) throws SQLException {
        BookDAO bookDAO = new BookDAO();
        return bookDAO.checkBook(ISBN);
    }
}
