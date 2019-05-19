package com.epam.library.dataBase;

import com.epam.library.entity.Order;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface OrderDAO <T extends Order> extends CommonDAO {

    void updateAcceptedDate(Date acceptedDate, int idOrder) throws SQLException;
    Order getOrderExecuting(int idLanguage, int idOrder) throws SQLException;
    List<T> getNewOrders(int idLanguage, int idStatus) throws SQLException;
    void createBooking(int idBook, int idUser, Date todayDate) throws SQLException;
    void updateBooking(Date orderDate, Date returnDate, int status, int idOrder) throws SQLException;
    void changeStatus(int status, int idOrder) throws SQLException;
    void updateActuallyReturnBooking(Date actuallyReturn, int idStatus, int idOrder) throws SQLException;
    List<T> getPersonalOrders(int idLanguage, int idUser) throws SQLException;
}
