/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.line;

import com.kamike.db.generic.GenericSelect;
import com.tushana.entity.Line;
import com.zlfun.framework.db.GenericReader;
import com.zlfun.framework.db.SqlDatabase;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class LineReader extends GenericReader<Line> {

    @Override
    public GenericSelect createSelect() {
        return new LineSelect();
    }

    @Override
    public GenericSelect<Line> createSelect(Line t) {
        return new LineSelect(t);
    }

    @Override
    public ArrayList<Line> find(Line t) {
        GenericSelect<Line> select = createSelect();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        ArrayList<Line> ret = null;
        try {
            conn = SqlDatabase.getInstance().fetch();
            ps = conn.prepareStatement(select.sql(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int index = select.bind(ps);
            rs = ps.executeQuery();
            ret = select.fetch(rs);

        } catch (Exception e) {
            ret = new ArrayList<>();
            System.out.println(this.getClass().getName() + e.toString());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(GenericReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;
    }

    public long countPlayerInGame(String game_id) {
        GenericSelect<Line> select = createSelect();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        long ret = 0L;
        try {
            conn = SqlDatabase.getInstance().fetch();
            ps = conn.prepareStatement(select.countSQL("select id from game_id="), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, game_id);
            rs = ps.executeQuery();
            ret = select.count(rs);

        } catch (Exception e) {
            ret = 0L;
            System.out.println(this.getClass().getName() + e.toString());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(GenericReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;
    }

     

}
