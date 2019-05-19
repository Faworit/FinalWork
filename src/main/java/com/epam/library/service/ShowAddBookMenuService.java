package com.epam.library.service;

import com.epam.library.dataBase.impl.AuthorDAOImpl;
import com.epam.library.dataBase.impl.GenreDAOImpl;
import com.epam.library.dataBase.impl.LanguageDAOImpl;
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
        HttpSession session = request.getSession(true);

        LanguageDAOImpl languageDAO = new LanguageDAOImpl();
        GenreDAOImpl genreDAO = new GenreDAOImpl();
        AuthorDAOImpl authorDAO = new AuthorDAOImpl();
        int idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        List<Genre> genres = genreDAO.getAll(idLanguage);
        List<Author> authors = authorDAO.getAll();

        session.setAttribute("list", genres);
        session.setAttribute("listAuthors", authors);
        response.sendRedirect("/jsp/addBook.jsp");
    }
}
