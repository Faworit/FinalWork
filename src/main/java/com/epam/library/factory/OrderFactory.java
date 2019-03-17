package com.epam.library.factory;

import com.epam.library.dataBase.UserDAO;
import com.epam.library.entity.Order;
import com.epam.library.entity.User;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderFactory {

    private static final Logger log = Logger.getLogger("UserDAO");

    public static Order collectOrder(ResultSet resultSet){
        List<User> users;
        Order order = new Order();
        UserDAO userDAO = new UserDAO();
        try {
            order.setOrderID(resultSet.getInt("ID_ORDER"));
            order.setBookTitle(resultSet.getString("TITLE"));
            order.setOrderDate(resultSet.getDate("ORDER_DATE"));
            order.setAcceptedDate(resultSet.getDate("ACCEPTED_DATE"));
            order.setReturnDate(resultSet.getDate("RETURN_DATE"));
            order.setActuallyReturn(resultSet.getDate("ACTUALLY_RETURNED"));
            order.setState(resultSet.getString("NAME"));
            users = userDAO.getUserByID(resultSet.getInt("ID_ORDER"));
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getRole().equalsIgnoreCase("reader")) {
                    order.setReader(user);
                } else {
                    order.setLibrarian(user);
                }
            }

        } catch (SQLException e) {
            log.error(e);
        }
        return order;
    }
}
