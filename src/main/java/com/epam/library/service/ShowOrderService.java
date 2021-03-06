package com.epam.library.service;

import com.epam.library.dataBase.impl.LanguageDAOImpl;
import com.epam.library.dataBase.impl.OrderDAOImpl;
import com.epam.library.entity.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowOrderService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession(true);

        String role = (String) session.getAttribute("role");
        LanguageDAOImpl languageDAO = new LanguageDAOImpl();
        int idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));

        OrderDAOImpl orderDAO = new OrderDAOImpl();
        List<Order> bookings;
        if(role.equals("reader")){
            int idUser = (int) session.getAttribute("idUser");
            bookings = orderDAO.getPersonalOrders(idLanguage, idUser);

            session.setAttribute("list", bookings);
            response.sendRedirect("orders.jsp");
        }
        if(role.equals("librarian")){
            bookings = orderDAO.getAll(idLanguage);

            session.setAttribute("list", bookings);
            response.sendRedirect("orders.jsp");
        }
    }
}
