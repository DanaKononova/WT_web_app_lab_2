package com.wt.lab2.model.entities.jewelry;

import com.wt.lab2.model.exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JewelryExtractor {

    public List<Jewelry> extractData(ResultSet resultSet) throws SQLException, DaoException {
        List<Jewelry> jewelries = new ArrayList<>();
        while (resultSet.next()) {
            Jewelry jewelry = new Jewelry();
            jewelry.setId(resultSet.getLong("ID"));
            jewelry.setImageUrl(resultSet.getString("IMAGEURL"));
            jewelry.setDescription(resultSet.getString("DESCRIPTION"));
            jewelry.setMaterial(resultSet.getString("MATERIAL"));
            jewelry.setStones(resultSet.getString("STONES"));
            jewelry.setType(resultSet.getString("TYPE"));
            jewelry.setCollection(resultSet.getString("COLLECTION"));
            jewelry.setPrice(resultSet.getBigDecimal("PRICE"));
            jewelries.add(jewelry);
        }

        return jewelries;
    }
}
