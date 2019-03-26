package com.epam.library.service;

import com.epam.library.dataBase.AuthorDAO;
import com.epam.library.dataBase.BookDAO;
import com.epam.library.dataBase.GenreDAO;
import com.epam.library.dataBase.LanguageDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditBookService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int idBook = Integer.parseInt(request.getParameter("ID"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int idAuthor = Integer.parseInt(request.getParameter("idAuthor"));
        int idLanguage;
        String[] idGenres = request.getParameterValues("idGenre");
        String title = request.getParameter("title");
        String[] genres = request.getParameterValues("genre");
        String[] authorNames = request.getParameterValues("name");
        String[] authorSurnames = request.getParameterValues("surname");
        String ISBN = request.getParameter("ISBN");
        List<Integer> idGenreConvert = convertFromString(idGenres);
        BookDAO bookDAO = new BookDAO();
        AuthorDAO authorDAO = new AuthorDAO();
        GenreDAO genreDAO = new GenreDAO();
        LanguageDAO languageDAO = new LanguageDAO();
        HttpSession session = request.getSession(true);
        idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        bookDAO.editBook(title, ISBN, quantity, idBook, idLanguage);
        for (int i = 0; i < authorNames.length; i++) {
            authorDAO.editAuthor(authorNames[i], authorSurnames[i], idAuthor);
        }
        for (int i = 0; i < genres.length; i++) {
            genreDAO.setEditGenre(idGenreConvert.get(i), idLanguage, genres[i]);
        }

        response.sendRedirect("/jsp/user.jsp");
    }

    private List<Integer> convertFromString(String[] idGenres){
        List<Integer> ID = new ArrayList<>();
        for (int i = 0; i < idGenres.length; i++) {
            int idAfterConvert = Integer.parseInt(idGenres[i]);
            ID.add(idAfterConvert);
        }
        return ID;
    }
}
