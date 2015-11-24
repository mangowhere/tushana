/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.pk;

import com.tushana.entity.photo.*;
import com.tushana.entity.line.*;
import com.tushana.entity.Pk;
import com.zlfun.framework.db.GenericWriter;
import com.zlfun.framework.db.Transaction;
 

/**
 *
 * @author Administrator
 */
public class PkWriter extends GenericWriter<Pk> {

    public PkWriter(Transaction ts) {
        super(ts);
    }

    
}
