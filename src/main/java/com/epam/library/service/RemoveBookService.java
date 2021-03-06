package com.epam.library.service;

import com.epam.library.dataBase.impl.BookDAOImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.library.dataBase.impl.BookDAOImpl.REMOVE_BOOK_BY_ID;

public class RemoveBookService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int idBook = Integer.parseInt(request.getParameter("ID"));
        BookDAOImpl bookDAO = new BookDAOImpl();
        bookDAO.editByID(idBook, REMOVE_BOOK_BY_ID);
        ShowBookService showBookService = new ShowBookService();
        showBookService.execute(request, response);
    }
}
