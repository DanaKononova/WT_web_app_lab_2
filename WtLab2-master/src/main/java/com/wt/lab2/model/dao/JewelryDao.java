package com.wt.lab2.model.dao;

import com.wt.lab2.model.entities.jewelry.Jewelry;
import com.wt.lab2.model.exceptions.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * @author dana
 * @version 1.0
 */
public interface JewelryDao {
    /**
     * Find jewelry by id
     *
     * @param id id of jewelry
     * @return jewelry with id
     * @throws DaoException throws when there is some errors during dao method execution
     */
    Optional<Jewelry> getJewelryById(Long id) throws DaoException;

    /**
     * Find cars from database
     *
     * @param offset    - offset of found jewelry
     * @param limit     - limit of found jewelry
     * @param query     - query for find
     * @return List of jewelry
     * @throws DaoException throws when there is some errors during dao method execution
     */

    List<Jewelry> getJewelry(int offset, int limit, String query) throws DaoException;
}
