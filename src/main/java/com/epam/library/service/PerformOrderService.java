package com.epam.library.service;

import com.epam.library.dataBase.LanguageDAO;
import com.epam.library.dataBase.OrderDAO;
import com.epam.library.dataBase.StatusDAO;
import com.epam.library.dataBase.UserDAO;
import com.epam.library.entity.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PerformOrderService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

        HttpSession session = request.getSession(true);

        LanguageDAO languageDAO = new LanguageDAO();
        int idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        int idOrder = Integer.parseInt(request.getParameter("idOrder"));

        UserDAO userDAO = new UserDAO();
        if(request.getParameter("userName").isEmpty()){
            int idUser = (int) session.getAttribute("idUser");
            userDAO.insertPerformer(idOrder, idUser);
        }

        OrderDAO orderDAO = new OrderDAO();
        Date todayDate = new Date();
        orderDAO.updateAcceptedDate(todayDate, idOrder);
        Order booking = orderDAO.getOrderExecuting(idLanguage, idOrder);

        StatusDAO statusDAO = new StatusDAO();
        List<String> statuses = statusDAO.getStatuses(idLanguage);

        session.setAttribute("booking", booking);
        session.setAttribute("list", statuses);

        response.sendRedirect("orderProcessing.jsp");
    }
}
