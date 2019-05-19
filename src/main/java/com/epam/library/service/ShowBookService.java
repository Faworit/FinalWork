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
        HttpSession session = request.getSession(true);

        LanguageDAO languageDAO = new LanguageDAO();
        BookDAO bookDAO = new BookDAO();
        int idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        List<Book> books = bookDAO.getAll(idLanguage);

        session.setAttribute("list", books);
        response.sendRedirect("jsp/user.jsp");
    }
}
