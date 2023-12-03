package com.wt.lab2.model.dao;

import com.wt.lab2.model.entities.car.Car;
import com.wt.lab2.model.exceptions.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * @author nekit
 * @version 1.0
 */
public interface CarDao {
    /**
     * Find car by id
     *
     * @param id id of car
     * @return car with id
     * @throws DaoException throws when there is some errors during dao method execution
     */
    Optional<Car> getCarById(Long id) throws DaoException;

    /**
     * Find cars from database
     *
     * @param offset    - offset of found cars
     * @param limit     - limit of found cars
     * @param query     - query for find
     * @return List of cars
     * @throws DaoException throws when there is some errors during dao method execution
     */

    List<Car> getCars(int offset, int limit, String query) throws DaoException;
}
