package com.epam.library.service;

import com.epam.library.dataBase.impl.OrderDAOImpl;
import com.epam.library.dataBase.impl.StatusDAOImpl;
import com.epam.library.entity.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditBookingService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            ParseException, SQLException {

        HttpSession session = request.getSession(true);

        StatusDAOImpl statusDAO = new StatusDAOImpl();
        Order order = (Order) session.getAttribute("booking");
        int idOrder = order.getId();
        String status = request.getParameter("status");
        int idStatus = statusDAO.getStatusID(status);

        Date orderDate;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        OrderDAOImpl orderDAO = new OrderDAOImpl();
        if(request.getParameter("orderDate")!=null && request.getParameter("returnDate")!=null) {
            orderDate = format.parse(request.getParameter("orderDate"));
            Date returnDate = format.parse(request.getParameter("returnDate"));
            Date todayDate = new Date();
            if (returnDate.before(todayDate)) {
                session.setAttribute("error", "not correct date");
                dispatcher = request.getRequestDispatcher("orderProcessing.jsp");
                dispatcher.forward(request, response);
            } else {
                orderDAO.updateBooking(orderDate, returnDate, idStatus, idOrder);

            }
        }
        if (!request.getParameter("actuallyReturn").isEmpty() && request.getParameter("orderDate")!=null) {
            orderDate = format.parse(request.getParameter("orderDate"));
            Date actuallyReturn = format.parse(request.getParameter("actuallyReturn"));
            if (actuallyReturn.before(orderDate)) {
                session.setAttribute("error", "not correct date");
                dispatcher = request.getRequestDispatcher("jsp/orderProcessing.jsp");
                dispatcher.forward(request, response);
            } else {
                orderDAO.updateActuallyReturnBooking(actuallyReturn, idStatus, idOrder);
            }
        }
        else {
            orderDAO.changeStatus(idStatus, idOrder);
        }






        ShowOrderService showOrderService = new ShowOrderService();
        showOrderService.execute(request, response);
    }
}
