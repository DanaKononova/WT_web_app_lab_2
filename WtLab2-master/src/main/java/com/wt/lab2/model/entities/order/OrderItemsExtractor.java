package com.wt.lab2.model.entities.order;

import com.wt.lab2.model.dao.JewelryDao;
import com.wt.lab2.model.dao.impl.JdbcJewelryDao;
import com.wt.lab2.model.exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsExtractor {
    public List<OrderItem> extractData(ResultSet resultSet) throws SQLException, DaoException {
        List<OrderItem> orderItems = new ArrayList<>();
        JewelryDao jewelryDao = JdbcJewelryDao.getInstance();
        while (resultSet.next()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setJewelry(jewelryDao.getJewelryById(resultSet.getLong("jewelryId")).orElse(null));
            orderItem.setQuantity(resultSet.getInt("quantity"));
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
