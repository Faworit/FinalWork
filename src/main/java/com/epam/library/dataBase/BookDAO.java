package com.epam.library.dataBase;

import com.epam.library.entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO extends CommonDAO<Book> {
    int getLastIdBook() throws SQLException;
    Book getBookByID(int idBook, int language) throws SQLException;
    void setIntoBook2Author(int idBook, int idAuthor) throws SQLException;
    boolean checkBook(String ISBN) throws SQLException;
    void editBook(String title, String ISBN, int quantity, int idBook, int idLanguage) throws SQLException;
    void editByID(int bookID, String query) throws SQLException;
    List<Book> searchBook(List<String> argumentsLike, String requestToDB, int language) throws SQLException;

}
