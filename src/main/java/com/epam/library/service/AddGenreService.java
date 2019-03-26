package com.epam.library.service;

import com.epam.library.dataBase.GenreDAO;
import com.epam.library.dataBase.LanguageDAO;
import com.epam.library.validator.GenreValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AddGenreService implements Service {

    RequestDispatcher dispatcher;
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int idGenre;
        int idRussianLanguage;
        int idEnglishLanguage;
        String genreNameRU = request.getParameter("genreNameRU");
        String genreNameENG = request.getParameter("genreNameENG");
        LanguageDAO languageDAO = new LanguageDAO();
        GenreDAO genreDAO = new GenreDAO();
        idGenre = genreDAO.getLastId()+1;
        System.out.println(idGenre);
        idRussianLanguage = languageDAO.getIdLanguage("RU");
        idEnglishLanguage = languageDAO.getIdLanguage("ENG");
        boolean isExistsNameRU = GenreValidator.checkGenre(genreNameRU, idRussianLanguage);
        boolean isExistsNameENG = GenreValidator.checkGenre(genreNameENG, idEnglishLanguage);
        if(isExistsNameRU){
            request.setAttribute("errorNameRU", "Genre with that name exists");
            dispatcher = request.getRequestDispatcher("addGenre.jsp");
            dispatcher.forward(request, response);
        } else if(isExistsNameENG){
            request.setAttribute("errorNameENG", "Genre with that name exists");
            dispatcher = request.getRequestDispatcher("addGenre.jsp");
            dispatcher.forward(request, response);
        } else {
            genreDAO.addGenre(idGenre, idEnglishLanguage, genreNameENG);
            genreDAO.addGenre(idGenre, idRussianLanguage, genreNameRU);
            ShowAddBookMenuService showAddBookMenuService = new ShowAddBookMenuService();
            showAddBookMenuService.execute(request, response);
        }
    }
}
