package com.epam.library.dataBase.impl;

import com.epam.library.dataBase.BookDAO;
import com.epam.library.dataBase.ConnectionPool;
import com.epam.library.entity.Book;
import com.epam.library.factory.BookFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    private static final String GET_FROM_BOOK_TABLE = "SELECT ID_BOOK, ID_LANGUAGE, TITLE, ISBN, QUANTITY " +
            "FROM BOOK WHERE ID_LANGUAGE =?";
    private static final String CHECK_BOOK_BY_ISBN = "SELECT ISBN FROM BOOK WHERE ISBN=?";
    private static final String GET_BOOK_BY_ID = "SELECT ID_BOOK, ID_LANGUAGE, TITLE, ISBN, QUANTITY FROM BOOK WHERE " +
            "ID_LANGUAGE=? AND ID_BOOK=?";
    private static final String GET_LAST_BOOK_ID = "SELECT MAX(ID_BOOK) FROM BOOK";
    private static final String ADD_BOOK = "INSERT INTO BOOK (ID_BOOK, ID_LANGUAGE, TITLE, ISBN, QUANTITY) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_INTO_BOOK2AUTHOR = "INSERT INTO BOOK2AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (?, ?)";
    private static final String EDIT_BOOK = "UPDATE book SET TITLE = ?, ISBN = ?, QUANTITY = ? WHERE (ID_BOOK = ?) " +
            "and (ID_LANGUAGE = ?)";
    public static final String REMOVE_BOOK_BY_ID = "DELETE FROM book WHERE (ID_BOOK = ?)";
    public static final String UPDATE_QUANTITY = "UPDATE BOOK SET QUANTITY = QUANTITY-1 WHERE ID_BOOK = ?";

    private ConnectionPool connectionPool;
    private Connection connection = null;

    @Override
    public int getLastIdBook() throws SQLException {
        int lastId = 0;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_BOOK_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                lastId = resultSet.getInt("MAX(ID_BOOK)");
            }
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return lastId;
    }

    @Override
    public Book getBookByID(int idBook, int language) throws SQLException {
        Book book = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_BY_ID)) {
            preparedStatement.setInt(1, language);
            preparedStatement.setInt(2, idBook);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book = BookFactory.createBook(resultSet, language);
                }
            }
        finally {
            connectionPool.returnConnection(connection);
        }
        return book;
    }

    @Override
    public void setIntoBook2Author(int idBook, int idAuthor) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_BOOK2AUTHOR)){
            preparedStatement.setInt(1, idBook);
            preparedStatement.setInt(2, idAuthor);
            preparedStatement.executeUpdate();
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public boolean checkBook(String ISBN) throws SQLException {
        boolean isAvailable = false;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_BOOK_BY_ISBN)) {
            preparedStatement.setString(1, ISBN);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                isAvailable = true;
            }
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return isAvailable;
    }

    public void editBook(String title, String ISBN, int quantity, int idBook, int idLanguage) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(EDIT_BOOK)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, ISBN);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setInt(4, idBook);
            preparedStatement.setInt(5, idLanguage);
            preparedStatement.executeUpdate();
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void editByID(int bookID, String query) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookID);
            preparedStatement.executeUpdate();
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List searchBook(List argumentsLike, String requestToDB, int language) throws SQLException {
        List<Book> books = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(requestToDB)) {
            preparedStatement.setInt(1, language);
            for (int i = 0; i < argumentsLike.size(); i++) {
                int index = i + 2;
                preparedStatement.setString(index, String.valueOf(argumentsLike.get(i)));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = BookFactory.createBook(resultSet, language);
                books.add(book);
            }
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return books;
    }

    @Override
    public List getAll(int id) throws SQLException {
        List<Book> books = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_FROM_BOOK_TABLE)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = BookFactory.createBook(resultSet, id);
                books.add(book);
            }
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return books;
    }

    @Override
    public void create(Object object) throws SQLException {
        Book book = (Book) object;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_BOOK)) {
            preparedStatement.setInt(1, book.getId());
            preparedStatement.setInt(2, book.getLanguageID());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setString(4, book.getISBN());
            preparedStatement.setInt(5, book.getQuantity());
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
}
