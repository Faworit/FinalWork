package com.epam.library.service;

import com.epam.library.dataBase.impl.BookDAOImpl;
import com.epam.library.dataBase.impl.LanguageDAOImpl;
import com.epam.library.entity.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class GetBookService {

     public static Book getBookFromDB(HttpServletRequest request) throws SQLException {

         BookDAOImpl bookDAO = new BookDAOImpl();
         HttpSession session = request.getSession(true);
         int idBook = Integer.parseInt(request.getParameter("ID"));
         int idLanguage;
         LanguageDAOImpl languageDAO = new LanguageDAOImpl();
         idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
         Book book = bookDAO.getBookByID(idBook, idLanguage);
         return book;
     }
}
