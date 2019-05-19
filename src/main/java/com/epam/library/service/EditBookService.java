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
        HttpSession session = request.getSession(true);

        BookDAO bookDAO = new BookDAO();
        LanguageDAO languageDAO = new LanguageDAO();
        String title = request.getParameter("title");
        String ISBN = request.getParameter("ISBN");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int idBook = Integer.parseInt(request.getParameter("ID"));
        int idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        bookDAO.editBook(title, ISBN, quantity, idBook, idLanguage);

        String[] authorNames = request.getParameterValues("name");
        String[] authorSurnames = request.getParameterValues("surname");
        for (int i = 0; i < authorNames.length; i++) {
            AuthorDAO authorDAO = new AuthorDAO();
            int idAuthor = Integer.parseInt(request.getParameter("idAuthor"));
            authorDAO.editAuthor(authorNames[i], authorSurnames[i], idAuthor);
        }

        String[] genres = request.getParameterValues("genre");
        for (int i = 0; i < genres.length; i++) {
            GenreDAO genreDAO = new GenreDAO();
            String[] idGenres = request.getParameterValues("idGenre");
            List<Integer> idGenreConvert = convertFromString(idGenres);
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
