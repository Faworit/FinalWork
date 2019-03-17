package com.epam.library.service;

import com.epam.library.entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowEditBookMenuService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Book book;
        book = GetBookService.getBookFromDB(request, response);
        request.setAttribute("book", book);
        dispatcher = request.getRequestDispatcher("jsp/editBook.jsp");
        dispatcher.forward(request, response);
    }
}
