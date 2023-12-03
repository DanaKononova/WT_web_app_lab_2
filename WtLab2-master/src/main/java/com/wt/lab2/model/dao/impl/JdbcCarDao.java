package com.wt.lab2.model.dao.impl;

import com.wt.lab2.model.dao.CarDao;
import com.wt.lab2.model.entities.car.Car;
import com.wt.lab2.model.entities.car.CarExtractor;
import com.wt.lab2.model.exceptions.DaoException;
import com.wt.lab2.model.utils.ConnectionPool;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Using jdbc to work with cars
 *
 * @author nekit
 * @version 1.0
 */
public class JdbcCarDao implements CarDao {
    /**
     * Instance of logger
     */
    private static final Logger log = Logger.getLogger(CarDao.class);
    /**
     * Instance of carExtractor
     */
    private final CarExtractor carExtractor = new CarExtractor();
    /**
     * Instance of carDao
     */
    private static volatile CarDao instance;
    /**
     * Instance of ConnectionPool
     */
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    /**
     * SQL query to find cars by id
     */
    private static final String GET_QUERY = "SELECT * FROM cars WHERE id = ?";
    /**
     * SQL query to find all cars with available stock > 0, limit and offset
     */
    private static final String SIMPLE_FIND_ALL_QUERY = "SELECT * FROM cars offset ? limit ?";
    /**
     * SQL query to find all cars with available stock
     */
    private static final String FIND_WITHOUT_OFFSET_AND_LIMIT = "SELECT * FROM cars WHERE ";

    private final ReadWriteLock lock = new ReentrantReadWriteLock();


    /**
     * Realisation of Singleton pattern
     *
     * @return instance of carDao
     */
    public static CarDao getInstance() {
        if (instance == null) {
            synchronized (CarDao.class) {
                if (instance == null) {
                    instance = new JdbcCarDao();
                }
            }
        }
        return instance;
    }

    /**
     * Get car by id from database
     *
     * @param id id of car
     * @return car
     * @throws DaoException throws when there is some errors during dao method execution
     */
    @Override
    public Optional<Car> getCarById(Long id) throws DaoException {
        Optional<Car> car;
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            lock.readLock().lock();
            conn = connectionPool.getConnection();
            statement = conn.prepareStatement(GET_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            car = carExtractor.extractData(resultSet).stream().findAny();
            log.log(Level.INFO, "Found cars by id in the database");
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Error in get function", ex);
            throw new DaoException("Error in process of getting car");
        } finally {
            lock.readLock().unlock();
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    log.log(Level.ERROR, "Error in closing statement", ex);
                }
            }
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return car;
    }

    /**
     * Find all cars from database
     *
     * @param offset - offset of found cars
     * @param limit  - limit of found cars
     * @param query  - query for find
     * @return list of cars
     * @throws DaoException throws when there is some errors during dao method execution
     */
    @Override
    public List<Car> getCars(int offset, int limit, String query) throws DaoException {
        List<Car> cars;
        String sql = makeFindAllSQL(query);
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            lock.readLock().lock();
            conn = connectionPool.getConnection();
            statement = conn.prepareStatement(sql);
            statement.setInt(1, offset);
            statement.setInt(2, limit);
            ResultSet resultSet = statement.executeQuery();
            cars = carExtractor.extractData(resultSet);
            log.log(Level.INFO, "Found all cars in the database");
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Error in finding cars", ex);
            throw new DaoException("Error in process of getting all cars");
        } finally {
            lock.readLock().unlock();
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    log.log(Level.ERROR, "Error in closing statement", ex);
                }
            }
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return cars;
    }

    /**
     * Make sql query with sorting and finding
     *
     * @param query - query to find
     * @return sql query
     */
    private String makeFindAllSQL(String query) {
        if (query != null && !query.equals("")) {
            return FIND_WITHOUT_OFFSET_AND_LIMIT + "(" + "LOWER(CARS.MARK) LIKE LOWER('" + query + "%') " +
                    "OR LOWER(CARS.MARK) LIKE LOWER('% " + query + "%') " +
                    "OR LOWER(CARS.SUBMARK) LIKE LOWER('" + query + "%') " +
                    "OR LOWER(CARS.SUBMARK) LIKE LOWER('% " + query + "%')" + ") " +
                    "offset ? limit ?";
        } else {
            return SIMPLE_FIND_ALL_QUERY;
        }
    }
}
