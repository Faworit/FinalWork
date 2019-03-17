package com.epam.library.dataBase;

import com.epam.library.entity.Author;
import com.epam.library.factory.AuthorFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    public static final String EDIT_AUTHOR = "UPDATE AUTHOR SET SURNAME=?, NAME=? WHERE ID_AUTHOR=?";
    public static final String GET_AUTOR = "SELECT A.ID_AUTHOR, A.SURNAME, A.NAME FROM BOOK B, AUTHOR A, BOOK2AUTHOR BA " +
            "WHERE B.ID_BOOK=BA.ID_BOOK AND A.ID_AUTHOR=BA.ID_AUTHOR AND B.ID_BOOK=? AND B.ID_LANGUAGE=?;";
    public static final String GET_ALL_AUTHOR = "SELECT NAME, SURNAME, ID_AUTHOR FROM AUTHOR";
    public static final String GET_AUTHOR_BY_NAME_SURNAME = "SELECT NAME, SURNAME FROM AUTHOR WHERE NAME = ? AND SURNAME = ?";
    public static final String ADD_AUTHOR = "INSERT INTO AUTHOR (SURNAME, NAME) VALUES (?, ?)";
    public static final String GET_ID_AUTHOR = "SELECT ID_AUTHOR FROM AUTHOR WHERE NAME = ? AND SURNAME = ?";
    private static final Logger log = Logger.getLogger("UserDAO");
    private ConnectionPool connectionPool;
    private Connection connection = null;

    public int getID(String name, String surname){
        int idAuthor = 0;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ID_AUTHOR)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                idAuthor = resultSet.getInt("ID_AUTHOR");
            }
        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return idAuthor;
    }

    public List<Author> getAllAuthors(){
        List<Author> authors = new ArrayList<>();
        Author author;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_AUTHOR)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                author = AuthorFactory.createAuthor(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return authors;
    }

    public void setAuthor(String name, String surname){
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_AUTHOR)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean isExist(String name, String surname){
        boolean isExists = false;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHOR_BY_NAME_SURNAME)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isExists = true;
            }
        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return isExists;
    }

    public List<Author> getAuthor(int IDBook, int language) {
        List<Author> authors = new ArrayList<>();
        Author author;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTOR)) {
            preparedStatement.setInt(1, IDBook);
            preparedStatement.setInt(2, language);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                author = AuthorFactory.createAuthor(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            log.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return authors;
    }

    public void editAuthor(String name, String surname, int idAuthor){
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(EDIT_AUTHOR)) {
            preparedStatement.setString(1, surname);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, idAuthor);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }
}
