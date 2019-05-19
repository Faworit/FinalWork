package com.epam.library.service;

import com.epam.library.entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class ShowEditBookMenuService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession(true);

        Book book = GetBookService.getBookFromDB(request);

        session.setAttribute("book", book);
        response.sendRedirect("editBook.jsp");
    }
}
