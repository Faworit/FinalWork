package com.epam.library.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BookGenreDAO {

    public static final String ADD_DATA_TO_BOOK2GENRE = "INSERT INTO BOOK2GENRE (ID_BOOK, ID_LANGUAGE, ID_GENRE) " +
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
}
