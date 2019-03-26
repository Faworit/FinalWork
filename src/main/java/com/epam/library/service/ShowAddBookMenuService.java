package com.epam.library.service;

import com.epam.library.dataBase.AuthorDAO;
import com.epam.library.dataBase.GenreDAO;
import com.epam.library.dataBase.LanguageDAO;
import com.epam.library.entity.Author;
import com.epam.library.entity.Genre;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowAddBookMenuService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int idLanguage;
        List<Genre> genres;
        List<Author> authors;
        HttpSession session = request.getSession(true);
        LanguageDAO languageDAO = new LanguageDAO();
        GenreDAO genreDAO = new GenreDAO();
        AuthorDAO authorDAO = new AuthorDAO();
        idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        genres = genreDAO.getAllGenres(idLanguage);
        authors = authorDAO.getAllAuthors();
        session.setAttribute("list", genres);
        session.setAttribute("listAuthors", authors);
        response.sendRedirect("/jsp/addBook.jsp");
    }
}
