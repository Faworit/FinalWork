package com.epam.library.service;

import com.epam.library.dataBase.LanguageDAO;
import com.epam.library.dataBase.OrderDAO;
import com.epam.library.entity.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowOrderService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idLanguage;
        int idUser;
        String role;
        List<Order> bookings;
        HttpSession session = request.getSession(true);
        idUser = (int) session.getAttribute("idUser");
        role = (String) session.getAttribute("role");
        LanguageDAO languageDAO = new LanguageDAO();
        OrderDAO orderDAO = new OrderDAO();
        idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        if(role.equals("reader")){
            bookings = orderDAO.getPersonalOrders(idLanguage, idUser);
            session.setAttribute("list", bookings);
            dispatcher = request.getRequestDispatcher("jsp/orders.jsp");
            dispatcher.forward(request, response);
        }
        if(role.equals("librarian")){
            bookings = orderDAO.getAllOrders(idLanguage);
            session.setAttribute("list", bookings);
            dispatcher = request.getRequestDispatcher("jsp/orders.jsp");
            dispatcher.forward(request, response);
        }


    }
}
