package com.epam.library.service;

import com.epam.library.dataBase.BookDAO;
import com.epam.library.dataBase.LanguageDAO;
import com.epam.library.entity.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowBookService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idLanguage;
        List<Book> books;
        HttpSession session = request.getSession(true);
        BookDAO bookDAO = new BookDAO();
        LanguageDAO languageDAO = new LanguageDAO();
        idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        books = bookDAO.getBook(idLanguage);
        session.setAttribute("list", books);
        response.sendRedirect("jsp/user.jsp");
    }
}
