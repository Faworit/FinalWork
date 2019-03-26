package com.epam.library.service;

import com.epam.library.dataBase.BookDAO;
import com.epam.library.dataBase.LanguageDAO;
import com.epam.library.entity.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowBookService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
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
