package com.epam.library.service;

import com.epam.library.dataBase.impl.BookDAOImpl;
import com.epam.library.dataBase.impl.LanguageDAOImpl;
import com.epam.library.entity.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession(true);

        String requestSearch = request.getParameter("search");
        List<String> argumentsLike = createArgumentLike(requestSearch);

        int countWordsSearch = argumentsLike.size();
        String requestDB = createRequest(countWordsSearch);

        LanguageDAOImpl languageDAO = new LanguageDAOImpl();
        int idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        BookDAOImpl bookDAO = new BookDAOImpl();
        List<Book> books = bookDAO.searchBook(argumentsLike, requestDB, idLanguage);

        session.setAttribute("list", books);
        response.sendRedirect("jsp/user.jsp");
    }

    private List<String> createArgumentLike(String requestSearch){
        List<String> argumentsLike = new ArrayList<>();
        String afterRemovePunctuation = requestSearch.replaceAll("[^а-яА-Яa-zA-Z]", " ").replaceAll("\\s+", " ");
        String[] wordsArray = afterRemovePunctuation.split(" ");
        ArrayList<String> wordsSearch = new ArrayList<>(Arrays.asList(wordsArray));

        StringBuilder stringBuilder;
        for (int i = 0; i < wordsSearch.size(); i++) {
            stringBuilder = new StringBuilder("%%");
            stringBuilder.insert(1, wordsSearch.get(i));
            String argument = stringBuilder.toString();
            argumentsLike.add(argument);
        }
        return argumentsLike;
    }

    private static String createRequest(int countWordsSearch){
        String firstPartOfRequest = "SELECT ID_BOOK, ID_LANGUAGE, TITLE, ISBN, QUANTITY FROM BOOK WHERE ID_LANGUAGE=? " +
                "AND TITLE LIKE ?";
        String partToInsert = " OR TITLE LIKE ?";
        String requestToDB;
        if(countWordsSearch>1) {
            StringBuilder stringBuilder = new StringBuilder(firstPartOfRequest);
            for (int i = 0; i < countWordsSearch-1; i++) {
                stringBuilder.insert(97, partToInsert);
            }
            requestToDB = stringBuilder.toString();
        }
        else{
            requestToDB = firstPartOfRequest;
        }
        return requestToDB;
    }
}
