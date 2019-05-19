package com.epam.library.service;

import com.epam.library.dataBase.impl.GenreDAOImpl;
import com.epam.library.dataBase.impl.LanguageDAOImpl;
import com.epam.library.entity.Genre;
import com.epam.library.validator.GenreValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddGenreService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        String genreNameRU = request.getParameter("genreNameRU");
        String genreNameENG = request.getParameter("genreNameENG");

        List<Genre> genres = createGenre(genreNameRU, genreNameENG);

        boolean isExist = GenreValidator.checkGenre(genres);
        if(isExist){
            request.setAttribute("errorNameRU", "Genre with that name exists");
            request.setAttribute("errorNameENG", "Genre with that name exists");
            dispatcher = request.getRequestDispatcher("addGenre.jsp");
            dispatcher.forward(request, response);
        } else {
            GenreDAOImpl genreDAO = new GenreDAOImpl();

            for (int i = 0; i < genres.size(); i++) {
              genreDAO.create(genres.get(i));
            }

            ShowAddBookMenuService showAddBookMenuService = new ShowAddBookMenuService();
            showAddBookMenuService.execute(request, response);
        }
    }

    private List<Genre> createGenre(String genreRU, String genreENG) throws SQLException {
        GenreDAOImpl genreDAO = new GenreDAOImpl();
        List<Genre> genres = new ArrayList<>();
        int idGenre = genreDAO.getLastId()+1;

        HashMap<String, Integer> genreName = getName(genreRU,genreENG);
        for(Map.Entry<String, Integer> item : genreName.entrySet()) {
            Genre genre = new Genre(item.getValue(), item.getKey());
            genre.setId(idGenre);
            genres.add(genre);
        }
        return genres;
    }

    private HashMap<String, Integer> getName(String genreRU, String genreENG) throws SQLException {
        LanguageDAOImpl languageDAO = new LanguageDAOImpl();
        int idRussianLanguage = languageDAO.getIdLanguage("RU");
        int idEnglishLanguage = languageDAO.getIdLanguage("ENG");

        HashMap<String, Integer> genreNames = new HashMap<>();
        genreNames.put(genreRU, idRussianLanguage);
        genreNames.put(genreENG, idEnglishLanguage);

        return genreNames;
    }
}
