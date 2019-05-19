package com.epam.library.service;

import com.epam.library.dataBase.BookDAO;
import com.epam.library.dataBase.LanguageDAO;
import com.epam.library.entity.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class GetBookService {

     public static Book getBookFromDB(HttpServletRequest request) throws SQLException {

         BookDAO bookDAO = new BookDAO();
         HttpSession session = request.getSession(true);
         int idBook = Integer.parseInt(request.getParameter("ID"));
         int idLanguage;
         LanguageDAO languageDAO = new LanguageDAO();
         idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
         Book book = bookDAO.getBookByID(idBook, idLanguage);
         return book;
     }
}
