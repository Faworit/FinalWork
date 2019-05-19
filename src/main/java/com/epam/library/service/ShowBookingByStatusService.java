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
import java.sql.SQLException;
import java.util.List;

public class ShowBookingByStatusService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession(true);

        String status = request.getParameter("status");
        LanguageDAO languageDAO = new LanguageDAO();
        OrderDAO orderDAO = new OrderDAO();
        StatusDAO statusDAO = new StatusDAO();

        int idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        int idStatus = statusDAO.getStatusID(status);
        List<Order> bookings = orderDAO.getNewOrders(idLanguage, idStatus);

        session.setAttribute("list", bookings);
        dispatcher = request.getRequestDispatcher("orders.jsp");
        dispatcher.forward(request, response);
    }
}
