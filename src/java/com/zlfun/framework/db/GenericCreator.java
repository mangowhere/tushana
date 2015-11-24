/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.db;

 
import com.zlfun.framework.db.generic.GenericCreate;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 * @param <T>
 */
public abstract class GenericCreator<T> {

    protected Transaction ts;
    protected GenericCreate<T> create;

    protected GenericCreator(Transaction ts) {

        this.ts = ts;
        create = newCreate();
    }

    
   

    
    
     

    public boolean createDatabase(String dbName) {
        if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();

        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(create.createDatebaseSQL(dbName));

            success = ps.executeUpdate();

        } catch (Exception e) {

            ts.rollback();
            System.out.println(this.getClass().getName() + e.toString());
            return false;

        } finally {
            try {

                if (ps != null) {
                    ps.close();
                    ps = null;
                }

            } catch (SQLException ex) {
                Logger.getLogger(GenericCreator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

    public boolean createTable() {
        if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();

        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(create.createTableSQL());

            success = ps.executeUpdate();

        } catch (Exception e) {

            ts.rollback();
            System.out.println(this.getClass().getName() + e.toString());
            return false;

        } finally {
            try {

                if (ps != null) {
                    ps.close();
                    ps = null;
                }

            } catch (SQLException ex) {
                Logger.getLogger(GenericCreator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

     

    public boolean createTable(String tableName) {
        if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();

        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(create.createTableSQL(tableName));

            success = ps.executeUpdate();

        } catch (Exception e) {

            ts.rollback();
            System.out.println(this.getClass().getName() + e.toString());
            return false;

        } finally {
            try {

                if (ps != null) {
                    ps.close();
                    ps = null;
                }

            } catch (SQLException ex) {
                Logger.getLogger(GenericCreator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

    public abstract GenericCreate<T> newCreate();
}
