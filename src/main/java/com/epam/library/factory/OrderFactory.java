package com.epam.library.factory;

import com.epam.library.dataBase.UserDAO;
import com.epam.library.entity.Order;
import com.epam.library.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderFactory {

    public static Order collectOrder(ResultSet resultSet) throws SQLException {

        Order order = new Order();

        order.setId(resultSet.getInt("ID_ORDER"));
        order.setBookTitle(resultSet.getString("TITLE"));
        order.setOrderDate(resultSet.getDate("ORDER_DATE"));
        order.setAcceptedDate(resultSet.getDate("ACCEPTED_DATE"));
        order.setReturnDate(resultSet.getDate("RETURN_DATE"));
        order.setActuallyReturn(resultSet.getDate("ACTUALLY_RETURNED"));
        order.setState(resultSet.getString("NAME"));

        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getUserByID(resultSet.getInt("ID_ORDER"));
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getRole().equalsIgnoreCase("reader")) {
                order.setReader(user);
            } else {
                order.setLibrarian(user);
            }
        }
        return order;
    }
}
