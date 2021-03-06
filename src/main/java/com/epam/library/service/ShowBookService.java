package com.epam.library.service;

import com.epam.library.dataBase.impl.BookDAOImpl;
import com.epam.library.dataBase.impl.LanguageDAOImpl;
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
        HttpSession session = request.getSession(true);

        LanguageDAOImpl languageDAO = new LanguageDAOImpl();
        BookDAOImpl bookDAO = new BookDAOImpl();
        int idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        List<Book> books = bookDAO.getAll(idLanguage);

        session.setAttribute("list", books);
        response.sendRedirect("jsp/user.jsp");
    }
}
