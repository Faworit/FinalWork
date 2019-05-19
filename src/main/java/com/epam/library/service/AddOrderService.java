package com.epam.library.service;

import com.epam.library.dataBase.impl.BookDAOImpl;
import com.epam.library.dataBase.impl.OrderDAOImpl;
import com.epam.library.entity.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class AddOrderService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession(true);

        Book book = GetBookService.getBookFromDB(request);
        int idBook = book.getId();
        int idUser = Integer.parseInt(String.valueOf(session.getAttribute("idUser")));
        Date todayDate = new Date();

        OrderDAOImpl orderDAO = new OrderDAOImpl();
        orderDAO.createBooking(idBook, idUser, todayDate);
        BookDAOImpl bookDAO = new BookDAOImpl();
        bookDAO.editByID(idBook, BookDAOImpl.UPDATE_QUANTITY);
        ShowOrderService showOrderService = new ShowOrderService();
        showOrderService.execute(request, response);
    }
}
