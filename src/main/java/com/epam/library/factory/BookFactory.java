package com.epam.library.factory;

import com.epam.library.dataBase.impl.AuthorDAOImpl;
import com.epam.library.dataBase.impl.GenreDAOImpl;
import com.epam.library.entity.Author;
import com.epam.library.entity.Book;
import com.epam.library.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookFactory {

    public static Book createBook(ResultSet resultSet, int language) throws SQLException {
        int IDBook = resultSet.getInt("ID_BOOK");

        AuthorDAOImpl authorDAO = new AuthorDAOImpl();
        List<Author> authors = authorDAO.getAuthor(IDBook, language);
        GenreDAOImpl genreDAO = new GenreDAOImpl();
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
