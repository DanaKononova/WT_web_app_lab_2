package com.wt.lab2.model.entities.car;

import com.wt.lab2.model.exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarExtractor {

    public List<Car> extractData(ResultSet resultSet) throws SQLException, DaoException {
        List<Car> cars = new ArrayList<>();
        while (resultSet.next()) {
            Car car = new Car();
            car.setId(resultSet.getLong("ID"));
            car.setImageUrl(resultSet.getString("IMAGEURL"));
            car.setDescription(resultSet.getString("DESCRIPTION"));
            car.setMark(resultSet.getString("MARK"));
            car.setSubMark(resultSet.getString("SUBMARK"));
            car.setType(resultSet.getString("TYPE"));
            car.setYears(resultSet.getString("YEARS"));
            car.setPrice(resultSet.getBigDecimal("PRICE"));
            cars.add(car);
        }

        return cars;
    }
}
