/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.picture;

import com.tushana.entity.photo.*;
import com.tushana.entity.line.*;
import com.kamike.db.generic.GenericSelect;
import com.tushana.entity.Picture;
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
public class PictureReader extends GenericReader<Picture> {

    @Override
    public GenericSelect createSelect() {
        return new PictureSelect();
    }

    @Override
    public GenericSelect<Picture> createSelect(Picture t) {
        return new PictureSelect(t);
    }

    @Override
    public ArrayList<Picture> find(Picture t) {
        GenericSelect<Picture> select = createSelect();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        ArrayList<Picture> ret = null;
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

    public Picture getByPhotoIdAndCategory(String photo_id, String category) {
        GenericSelect<Picture> select = createSelect();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        Picture ret = null;
        try {
            conn = SqlDatabase.getInstance().fetch();
            ps = conn.prepareStatement(select.rawSql() + " where photo_id=? and category=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, photo_id);
            ps.setString(2, category);
            rs = ps.executeQuery();
            ret = select.fetchOnce(rs);

        } catch (Exception e) {
            ret = null;
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
        GenericSelect<Picture> select = createSelect();

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
