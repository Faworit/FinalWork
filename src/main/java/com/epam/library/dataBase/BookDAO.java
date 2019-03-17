package com.epam.library.dataBase;

import com.epam.library.entity.Book;
import com.epam.library.factory.BookFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public static final String GET_FROM_BOOK_TABLE = "SELECT ID_BOOK, ID_LANGUAGE, TITLE, ISBN, QUANTITY " +
            "FROM BOOK WHERE ID_LANGUAGE =?";
    public static final String ADD_BOOK = "INSERT INTO BOOK (ID_BOOK, ID_LANGUAGE, TITLE, ISBN, QUANTITY) " +
            "VALUES (?, ?, ?, ?, ?)";
    public static final String CHECK_BOOK_BY_ISBN = "SELECT ISBN FROM BOOK WHERE ISBN=?";
    public static final String GET_BOOK_BY_ID = "SELECT ID_BOOK, ID_LANGUAGE, TITLE, ISBN, QUANTITY FROM BOOK WHERE " +
            "ID_LANGUAGE=? AND ID_BOOK=?";
    public static final String EDIT_BOOK = "UPDATE book SET TITLE = ?, ISBN = ?, QUANTITY = ? WHERE (ID_BOOK = ?) " +
            "and (ID_LANGUAGE = ?)";
    public static final String REMOVE_BOOK_BY_ID = "DELETE FROM book WHERE (ID_BOOK = ?)";
    public static final String UPDATE_QUANTITY = "UPDATE BOOK SET QUANTITY = QUANTITY-1 WHERE ID_BOOK = ?";
    public static final String GET_LAST_BOOK_ID = "SELECT MAX(ID_BOOK) FROM BOOK";
    public static final String INSERT_INTO_BOOK2AUTHOR = "INSERT INTO BOOK2AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (?, ?)";
    private static final Logger log = Logger.getLogger("UserDAO");
    private ConnectionPool connectionPool;
    private Connection connection = null;

    public int getLastIdBook(){
        int lastId = 0;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_BOOK_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                lastId = resultSet.getInt("MAX(ID_BOOK)");
            }
        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return lastId;
    }

    public List<Book> getBook(int language) {
        List<Book> books = new ArrayList<>();
        Book book;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_FROM_BOOK_TABLE)) {
            preparedStatement.setInt(1, language);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book = BookFactory.createBook(resultSet, language);
                books.add(book);
            }
        } catch (SQLException e) {
            log.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return books;
    }

    public Book getBookByID(int idBook, int language){
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
        catch (SQLException e) {
            log.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return book;
    }

    public void setIntoBook2Author(int idBook, int idAuthor){
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_BOOK2AUTHOR)){
            preparedStatement.setInt(1, idBook);
            preparedStatement.setInt(2, idAuthor);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public void addBook(int idBook, int idLanguage, String ISBN, int quantity, String title) {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_BOOK)) {
            preparedStatement.setInt(1, idBook);
            preparedStatement.setInt(2, idLanguage);
            preparedStatement.setString(3, title);
            preparedStatement.setString(4, ISBN);
            preparedStatement.setInt(5, quantity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }

    }

    public boolean checkBook(String ISBN) {
        boolean isAvailable = false;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_BOOK_BY_ISBN)) {
            preparedStatement.setString(1, ISBN);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                isAvailable = true;
            }
        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return isAvailable;
    }

    public void editBook(String title, String ISBN, int quantity, int idBook, int idLanguage){
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(EDIT_BOOK)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, ISBN);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setInt(4, idBook);
            preparedStatement.setInt(5, idLanguage);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    public void editByID(int bookID, String query) {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    public List<Book> searchBook(List<String> argumentsLike, String requestToDB, int language) {
        List<Book> books = new ArrayList<>();
        Book book;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(requestToDB)) {
            preparedStatement.setInt(1, language);
            for(int i=0; i<argumentsLike.size(); i++) {
                int index = i+2;
                preparedStatement.setString(index, argumentsLike.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book = BookFactory.createBook(resultSet, language);
                books.add(book);
            }
        } catch (SQLException e) {
            log.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return books;
    }
}
