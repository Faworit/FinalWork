package com.epam.library.service;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class EditBookingService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        int idOrder;
        int idStatus;
        String status = request.getParameter("status");
        String role;
        Date todayDate = new Date();
        Date orderDate;
        Date returnDate;
        Date actuallyReturn;
        Order order;
        OrderDAO orderDAO = new OrderDAO();
        StatusDAO statusDAO = new StatusDAO();
        ShowOrderService showOrderService = new ShowOrderService();
        HttpSession session = request.getSession(true);
        role = (String) session.getAttribute("role");
        order = (Order) session.getAttribute("booking");
        idOrder = order.getOrderID();
        idStatus = statusDAO.getStatusID(status);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(role.equalsIgnoreCase("librarian")){
            if(request.getParameter("orderDate")!=null && request.getParameter("returnDate")!=null) {
                orderDate = format.parse(request.getParameter("orderDate"));
                returnDate = format.parse(request.getParameter("returnDate"));
                if (orderDate.after(todayDate) && returnDate.before(todayDate)) {
                    session.setAttribute("error", "not correct date");
                    dispatcher = request.getRequestDispatcher("jsp/orderProcessing.jsp");
                    dispatcher.forward(request, response);
                } else {
                    orderDAO.updateBooking(orderDate, returnDate, idStatus, idOrder);
                }
            }
            if (!request.getParameter("actuallyReturn").isEmpty() && request.getParameter("orderDate")!=null) {
                orderDate = format.parse(request.getParameter("orderDate"));
                actuallyReturn = format.parse(request.getParameter("actuallyReturn"));
                if (actuallyReturn.before(orderDate)) {
                    session.setAttribute("error", "not correct date");
                    dispatcher = request.getRequestDispatcher("jsp/orderProcessing.jsp");
                    dispatcher.forward(request, response);
                } else {
                    orderDAO.updateActuallyReturnBooking(actuallyReturn, idStatus, idOrder);
                }
            }

        }
         else {
            orderDAO.changeStatus(idStatus, idOrder);
        }
        showOrderService.execute(request, response);
    }
}
