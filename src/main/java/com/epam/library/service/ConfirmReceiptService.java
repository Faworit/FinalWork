package com.epam.library.service;

import com.epam.library.dataBase.OrderDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ConfirmReceiptService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int idOrder = Integer.parseInt(request.getParameter("idOrder"));
        int idStatus = 5;
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.changeStatus(idStatus, idOrder);
        ShowOrderService showOrderService = new ShowOrderService();
        showOrderService.execute(request, response);
    }
}
