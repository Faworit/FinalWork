package com.epam.library.service;

import com.epam.library.dataBase.LanguageDAO;
import com.epam.library.dataBase.OrderDAO;
import com.epam.library.dataBase.StatusDAO;
import com.epam.library.dataBase.UserDAO;
import com.epam.library.entity.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class PerformOrderService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        int idOrder = Integer.parseInt(request.getParameter("idOrder"));
        int idUser;
        int idLanguage;
        List<String> statuses;
        Date todayDate = new Date();
        System.out.println(request.getParameter("userName"));
        Order booking;
        UserDAO userDAO = new UserDAO();
        OrderDAO orderDAO = new OrderDAO();
        StatusDAO statusDAO = new StatusDAO();
        LanguageDAO languageDAO = new LanguageDAO();
        HttpSession session = request.getSession(true);
        idUser = (int) session.getAttribute("idUser");
        idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        if(request.getParameter("userName").isEmpty()){
            System.out.println("hello");
            userDAO.insertPerformer(idOrder, idUser);
        }
        orderDAO.updateAcceptedDate(todayDate, idOrder);
        booking = orderDAO.getOrderExecuting(idLanguage, idOrder);
        statuses = statusDAO.getStatuses(idLanguage);
        session.setAttribute("booking", booking);
        session.setAttribute("list", statuses);
        dispatcher = request.getRequestDispatcher("orderProcessing.jsp");
        dispatcher.forward(request, response);
    }
}
