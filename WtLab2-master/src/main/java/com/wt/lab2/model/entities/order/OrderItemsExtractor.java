package com.wt.lab2.model.entities.order;

import com.wt.lab2.model.dao.CarDao;
import com.wt.lab2.model.dao.impl.JdbcCarDao;
import com.wt.lab2.model.exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsExtractor {
    public List<OrderItem> extractData(ResultSet resultSet) throws SQLException, DaoException {
        List<OrderItem> orderItems = new ArrayList<>();
        CarDao carDao = JdbcCarDao.getInstance();
        while (resultSet.next()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setcar(carDao.getCarById(resultSet.getLong("carId")).orElse(null));
            orderItem.setQuantity(resultSet.getInt("quantity"));
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
