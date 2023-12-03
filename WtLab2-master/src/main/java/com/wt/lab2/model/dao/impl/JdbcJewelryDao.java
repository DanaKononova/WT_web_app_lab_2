package com.wt.lab2.model.dao.impl;

import com.wt.lab2.model.dao.JewelryDao;
import com.wt.lab2.model.entities.jewelry.Jewelry;
import com.wt.lab2.model.entities.jewelry.JewelryExtractor;
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
 * Using jdbc to work with jewelry
 *
 * @author dana
 * @version 1.0
 */
public class JdbcJewelryDao implements JewelryDao {
    /**
     * Instance of logger
     */
    private static final Logger log = Logger.getLogger(JewelryDao.class);
    /**
     * Instance of JewelryExtractor
     */
    private final JewelryExtractor jewelryExtractor = new JewelryExtractor();
    /**
     * Instance of JewelryDao
     */
    private static volatile JewelryDao instance;
    /**
     * Instance of ConnectionPool
     */
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    /**
     * SQL query to find jewelry by id
     */
    private static final String GET_QUERY = "SELECT * FROM jewelry WHERE id = ?";
    /**
     * SQL query to find all jewelry with available stock > 0, limit and offset
     */
    private static final String SIMPLE_FIND_ALL_QUERY = "SELECT * FROM jewelry offset ? limit ?";
    /**
     * SQL query to find all jewelry with available stock
     */
    private static final String FIND_WITHOUT_OFFSET_AND_LIMIT = "SELECT * FROM jewelry WHERE ";

    private final ReadWriteLock lock = new ReentrantReadWriteLock();


    /**
     * Realisation of Singleton pattern
     *
     * @return instance of JewelryDao
     */
    public static JewelryDao getInstance() {
        if (instance == null) {
            synchronized (JewelryDao.class) {
                if (instance == null) {
                    instance = new JdbcJewelryDao();
                }
            }
        }
        return instance;
    }

    /**
     * Get jewelry by id from database
     *
     * @param id id of jewelry
     * @return car
     * @throws DaoException throws when there is some errors during dao method execution
     */
    @Override
    public Optional<Jewelry> getJewelryById(Long id) throws DaoException {
        Optional<Jewelry> jewelry;
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            lock.readLock().lock();
            conn = connectionPool.getConnection();
            statement = conn.prepareStatement(GET_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            jewelry = jewelryExtractor.extractData(resultSet).stream().findAny();
            log.log(Level.INFO, "Found jewelry by id in the database");
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Error in get function", ex);
            throw new DaoException("Error in process of getting jewelry");
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
        return jewelry;
    }

    /**
     * Find all jewelry from database
     *
     * @param offset - offset of found jewelry
     * @param limit  - limit of found jewelry
     * @param query  - query for find
     * @return list of jewelry
     * @throws DaoException throws when there is some errors during dao method execution
     */
    @Override
    public List<Jewelry> getJewelry(int offset, int limit, String query) throws DaoException {
        List<Jewelry> jewelries;
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
            jewelries = jewelryExtractor.extractData(resultSet);
            log.log(Level.INFO, "Found all jewelry in the database");
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Error in finding jewelry", ex);
            throw new DaoException("Error in process of getting all jewelry");
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
        return jewelries;
    }

    /**
     * Make sql query with sorting and finding
     *
     * @param query - query to find
     * @return sql query
     */
    private String makeFindAllSQL(String query) {
        if (query != null && !query.equals("")) {
            return FIND_WITHOUT_OFFSET_AND_LIMIT + "(" + "LOWER(JEWELRIES.MARK) LIKE LOWER('" + query + "%') " +
                    "OR LOWER(JEWELRIES.MARK) LIKE LOWER('% " + query + "%') " +
                    "OR LOWER(JEWELRIES.SUBMARK) LIKE LOWER('" + query + "%') " +
                    "OR LOWER(JEWELRIES.SUBMARK) LIKE LOWER('% " + query + "%')" + ") " +
                    "offset ? limit ?";
        } else {
            return SIMPLE_FIND_ALL_QUERY;
        }
    }
}
