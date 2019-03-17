package com.epam.library.service;

import com.epam.library.dataBase.LanguageDAO;
import com.epam.library.dataBase.OrderDAO;
import com.epam.library.dataBase.StatusDAO;
import com.epam.library.entity.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowBookingByStatusService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idLanguage;
        int idStatus;
        String status = request.getParameter("status");
        List<Order> bookings;
        HttpSession session = request.getSession(true);
        OrderDAO orderDAO = new OrderDAO();
        StatusDAO statusDAO = new StatusDAO();
        LanguageDAO languageDAO = new LanguageDAO();
        idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        idStatus = statusDAO.getStatusID(status);
        bookings = orderDAO.getNewOrders(idLanguage, idStatus);
        session.setAttribute("list", bookings);
        dispatcher = request.getRequestDispatcher("jsp/orders.jsp");
        dispatcher.forward(request, response);
    }
}
