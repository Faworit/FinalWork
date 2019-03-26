package com.epam.library.service;

import com.epam.library.dataBase.BookDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RemoveBookService implements Service {

    RequestDispatcher dispatcher;
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int idBook = Integer.parseInt(request.getParameter("ID"));
        BookDAO bookDAO = new BookDAO();
        bookDAO.editByID(idBook, BookDAO.REMOVE_BOOK_BY_ID);
        ShowBookService showBookService = new ShowBookService();
        showBookService.execute(request, response);
    }
}
