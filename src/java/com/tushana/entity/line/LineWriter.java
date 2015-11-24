/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.line;

import com.tushana.entity.Line;
import com.zlfun.framework.db.GenericWriter;
import com.zlfun.framework.db.Transaction;
 

/**
 *
 * @author Administrator
 */
public class LineWriter extends GenericWriter<Line> {

    public LineWriter(Transaction ts) {
        super(ts);
    }

    
}
