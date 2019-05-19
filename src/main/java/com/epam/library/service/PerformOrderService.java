package com.epam.library.service;

import com.epam.library.dataBase.impl.LanguageDAOImpl;
import com.epam.library.dataBase.impl.OrderDAOImpl;
import com.epam.library.dataBase.impl.StatusDAOImpl;
import com.epam.library.dataBase.impl.UserDAOImpl;
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

        LanguageDAOImpl languageDAO = new LanguageDAOImpl();
        int idLanguage = languageDAO.getIdLanguage(String.valueOf(session.getAttribute("language")));
        int idOrder = Integer.parseInt(request.getParameter("idOrder"));

        UserDAOImpl userDAO = new UserDAOImpl();
        if(request.getParameter("userName").isEmpty()){
            int idUser = (int) session.getAttribute("idUser");
            userDAO.insertPerformer(idOrder, idUser);
        }

        OrderDAOImpl orderDAO = new OrderDAOImpl();
        Date todayDate = new Date();
        orderDAO.updateAcceptedDate(todayDate, idOrder);
        Order booking = orderDAO.getOrderExecuting(idLanguage, idOrder);

        StatusDAOImpl statusDAO = new StatusDAOImpl();
        List<String> statuses = statusDAO.getStatuses(idLanguage);

        session.setAttribute("booking", booking);
        session.setAttribute("list", statuses);

        response.sendRedirect("orderProcessing.jsp");
    }
}
