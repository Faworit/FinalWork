package com.epam.library.service;

import com.epam.library.dataBase.BookDAO;
import com.epam.library.dataBase.LanguageDAO;
import com.epam.library.entity.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idLanguage;
        int countWordsSearch;
        String requestDB;
        String requestSearch;
        List<Book> books;
        List<String> argumentsLike;
        BookDAO bookDAO = new BookDAO();
        LanguageDAO languageDAO = new LanguageDAO();
        HttpSession session = request.getSession(true);
        idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        requestSearch = request.getParameter("search");
        argumentsLike = createArgumentLike(requestSearch);
        countWordsSearch = argumentsLike.size();
        requestDB = createRequest(countWordsSearch);
        books = bookDAO.searchBook(argumentsLike, requestDB, idLanguage);
        session.setAttribute("list", books);
        response.sendRedirect("jsp/user.jsp");
    }

    private List<String> createArgumentLike(String requestSearch){
        String argument;
        String afterRemovePunctuation;
        String[] wordsArray;
        ArrayList<String> wordsSearch;
        List<String> argumentsLike = new ArrayList<>();
        afterRemovePunctuation = requestSearch.replaceAll("[^а-яА-Яa-zA-Z]", " ").replaceAll("\\s+", " ");
        wordsArray = afterRemovePunctuation.split(" ");
        wordsSearch = new ArrayList<>(Arrays.asList(wordsArray));
        StringBuilder stringBuilder;
        for (int i = 0; i < wordsSearch.size(); i++) {
            stringBuilder = new StringBuilder("%%");
            stringBuilder.insert(1, wordsSearch.get(i));
            argument = stringBuilder.toString();
            argumentsLike.add(argument);
        }

        return argumentsLike;
    }

    private static String createRequest(int countWordsSearch){
        String firstPartOfRequest = "SELECT ID_BOOK, ID_LANGUAGE, TITLE, ISBN, QUANTITY FROM BOOK WHERE ID_LANGUAGE=? AND TITLE LIKE ?"; //select ID_BOOK, ID_LANGUAGE, TITLE, ISBN, QUANTITY from book where title like '%java%'
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
