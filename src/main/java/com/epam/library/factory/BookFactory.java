package com.epam.library.factory;

import com.epam.library.dataBase.AuthorDAO;
import com.epam.library.dataBase.GenreDAO;
import com.epam.library.entity.Author;
import com.epam.library.entity.Book;
import com.epam.library.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookFactory {

    public static Book createBook(ResultSet resultSet, int language) throws SQLException {
        int IDBook = resultSet.getInt("ID_BOOK");

        AuthorDAO authorDAO = new AuthorDAO();
        List<Author> authors = authorDAO.getAuthor(IDBook, language);
        GenreDAO genreDAO = new GenreDAO();
        List<Genre> genres = genreDAO.getGenre(IDBook, language);

        Book book = new Book();
        book.setTitle(resultSet.getString("TITLE"));
        book.setLanguageID(resultSet.getInt("ID_LANGUAGE"));
        book.setId(IDBook);
        book.setISBN(resultSet.getString("ISBN"));
        book.setQuantity(resultSet.getInt("QUANTITY"));


        book.setAuthors(authors);
        book.setGeners(genres);

        return book;
    }
}
