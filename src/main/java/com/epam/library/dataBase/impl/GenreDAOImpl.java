package com.epam.library.dataBase.impl;

import com.epam.library.dataBase.ConnectionPool;
import com.epam.library.dataBase.GenreDAO;
import com.epam.library.entity.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl implements GenreDAO {

    private static final String GET_GENRE = "SELECT G.ID_GENRE, G.ID_LANGUAGE, G.GENRE_NAME FROM BOOK B, GENRE G, " +
            "BOOK2GENRE BG WHERE B.ID_BOOK = BG.ID_BOOK AND G.ID_GENRE = BG.ID_GENRE AND B.ID_LANGUAGE = BG.ID_LANGUAGE " +
            "AND G.ID_LANGUAGE=BG.ID_LANGUAGE AND G.ID_GENRE = BG.ID_GENRE AND B.ID_BOOK=? AND B.ID_LANGUAGE=?";
    private static final String GET_ALL_GENRES = "SELECT * FROM GENRE WHERE ID_LANGUAGE=?";
    private static final String EDIT_GENRE = "UPDATE GENRE SET GENRE_NAME = ? WHERE (ID_GENRE = ?) and (ID_LANGUAGE = ?)";
    private static final String GET_LAST_ID_GENRE = "SELECT MAX(ID_GENRE) FROM GENRE";
    private static final String ADD_GENRE = "INSERT INTO GENRE (ID_GENRE, ID_LANGUAGE, GENRE_NAME) VALUES (?, ?, ?)";
    private ConnectionPool connectionPool;
    private Connection connection = null;

    @Override
    public List<Genre> getGenre(int IDBook, int language) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_GENRE)) {
            preparedStatement.setInt(1, IDBook);
            preparedStatement.setInt(2, language);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Genre genre = getResult(resultSet);
                genres.add(genre);
            }
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return genres;
    }

    private Genre getResult(ResultSet resultSet) throws SQLException {
        int IDGenre = resultSet.getInt("ID_GENRE");
        int IDLanguage = resultSet.getInt("ID_LANGUAGE");
        String genreName = resultSet.getString("GENRE_NAME");
        Genre genre = new Genre(IDLanguage, genreName);
        genre.setId(IDGenre);
        return genre;
    }

    @Override
    public void setEditGenre(int idGenre, int idLanguage, String genreName) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(EDIT_GENRE)) {
            preparedStatement.setString(1, genreName);
            preparedStatement.setInt(2, idGenre);
            preparedStatement.setInt(3, idLanguage);
            preparedStatement.executeUpdate();
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public int getLastId() throws SQLException {
        int lastId = 0;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_ID_GENRE)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                lastId = resultSet.getInt("MAX(ID_GENRE)");
            }
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return lastId;
    }

    @Override
    public List getAll(int id) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_GENRES)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Genre genre = getResult(resultSet);
                genres.add(genre);
            }
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return genres;
    }

    @Override
    public void create(Genre object) throws SQLException {
        Genre genre = object;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_GENRE)) {
            preparedStatement.setInt(1, genre.getId());
            preparedStatement.setInt(2, genre.getIDLanguage());
            preparedStatement.setString(3, genre.getGenreName());
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
