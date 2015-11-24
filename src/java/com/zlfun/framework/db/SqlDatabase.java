/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.db;

import java.sql.Connection;
import com.mchange.v2.c3p0.*;
import com.zlfun.framework.config.SystemConfig;
import java.beans.PropertyVetoException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brin
 */
public class SqlDatabase {

    private ComboPooledDataSource cpds;

    private static ReentrantLock lock = new ReentrantLock();
    private boolean ready = false;

    private static volatile SqlDatabase dbinst = new SqlDatabase();

    private SqlDatabase() {

    }

    public static SqlDatabase getInstance() {

        return dbinst;
    }

    private boolean init() {
        if (ready) {
            return true;
        }
        lock.lock();
        try {
            if (ready) {
                return true;
            }
            ready = true;
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass(SystemConfig.sqlDriver);
            cpds.setJdbcUrl(SystemConfig.sqlUrl);
            cpds.setUser(SystemConfig.sqlUser);
            cpds.setPassword(SystemConfig.sqlPass);
            cpds.setMaxStatements(SystemConfig.sqlMaxStatements);
            cpds.setInitialPoolSize(SystemConfig.sqlInitialPoolSize);
            cpds.setMinPoolSize(SystemConfig.sqlMinPoolSize);
            cpds.setMaxPoolSize(SystemConfig.sqlMaxPoolSize);
            cpds.setAcquireIncrement(SystemConfig.sqlAcquireIncrement);
            cpds.setMaxIdleTime(SystemConfig.sqlMaxIdleTime);
            cpds.setMaxIdleTimeExcessConnections(SystemConfig.sqlMaxIdleTimeExcessConnections);
            cpds.setMaxConnectionAge(SystemConfig.sqlMaxConnectionAge);
            cpds.setMaxStatementsPerConnection(SystemConfig.sqlMaxStatementsPerConnection);
            cpds.setNumHelperThreads(SystemConfig.sqlNumHelperThreads);
            cpds.setIdleConnectionTestPeriod(SystemConfig.sqlIdleConnectionTestPeriod);
            cpds.setCheckoutTimeout(SystemConfig.sqlCheckoutTimeout);
            cpds.setPreferredTestQuery(SystemConfig.sqlPreferredTestQuery);
            cpds.setAcquireRetryDelay(SystemConfig.sqlAcquireRetryDelay);
            cpds.setAcquireRetryAttempts(SystemConfig.sqlAcquireRetryAttempts);

            //cpds.setAutomaticTestTable();
        } catch (Exception ex) {
            Logger.getLogger(SqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
            ready = false;
            return false;
        } finally {
            lock.unlock();
        }
        return true;

    }

    public void destroy() {
        try {
            DataSources.destroy(cpds);
            ready = false;
        } catch (SQLException ex) {
            Logger.getLogger(SqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Connection fetch() {

        if (ready == false || cpds == null) {
            try {
                if (!init()) {
                    return getSingleConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            return cpds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(SqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return getSingleConnection();
        }

    }
    private Connection singleConnection;

    private Connection getSingleConnection() {
        if (singleConnection != null) {
            try {
                if (!singleConnection.isClosed()) {
                    singleConnection.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(SqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            singleConnection = null;
        }

        try {
            Class.forName(SystemConfig.sqlDriver).newInstance();
            try {
                singleConnection = DriverManager.getConnection(SystemConfig.sqlUrl, SystemConfig.sqlUser, SystemConfig.sqlPass);
            } catch (SQLException ex) {
                singleConnection = null;
                Logger.getLogger(SqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (InstantiationException ex) {
            singleConnection = null;
            Logger.getLogger(SqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            singleConnection = null;
            Logger.getLogger(SqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            singleConnection = null;
            Logger.getLogger(SqlDatabase.class.getName()).log(Level.SEVERE, null, e);
        }

        return singleConnection;

    }
}
