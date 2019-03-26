package com.epam.library.service;

import com.epam.library.dataBase.BookDAO;
import com.epam.library.dataBase.OrderDAO;
import com.epam.library.entity.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.TimeZone;

public class AddOrderService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        int idBook;
        int idUser;
        Date todayDate = new Date();
        Book book;
        OrderDAO orderDAO = new OrderDAO();
        BookDAO bookDAO = new BookDAO();
        HttpSession session = request.getSession(true);
        book = GetBookService.getBookFromDB(request);
        idBook = book.getID();
        idUser = Integer.parseInt(String.valueOf(session.getAttribute("idUser")));
        orderDAO.createBooking(idBook, idUser, todayDate);
        bookDAO.editByID(idBook, BookDAO.UPDATE_QUANTITY);
        ShowOrderService showOrderService = new ShowOrderService();
        showOrderService.execute(request, response);
    }
}
