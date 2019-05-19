package com.epam.library.dataBase.impl;

import com.epam.library.dataBase.CommonDAO;
import com.epam.library.dataBase.ConnectionPool;
import com.epam.library.entity.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BookGenreDAOImpl implements CommonDAO {

    private static final String ADD_DATA_TO_BOOK2GENRE = "INSERT INTO BOOK2GENRE (ID_BOOK, ID_LANGUAGE, ID_GENRE) " +
            "select book.id_book, book.id_language, genre.id_genre from book, genre where book.id_book=? and" +
            " book.id_language=? and genre.genre_name=?";
    private ConnectionPool connectionPool;
    private Connection connection = null;

    public void addData(int idBook, int idLanguage, List<String> genres) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        for(int i =0; i<genres.size(); i++){
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_DATA_TO_BOOK2GENRE)) {
                preparedStatement.setInt(1, idBook);
                preparedStatement.setInt(2, idLanguage);
                preparedStatement.setString(3, genres.get(i));
                preparedStatement.executeUpdate();
            }
            finally {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public List getAll(int id){
        throw new UnsupportedOperationException();
    }

    @Override
    public void create(Entity object) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id){
        throw new UnsupportedOperationException();
    }
}
