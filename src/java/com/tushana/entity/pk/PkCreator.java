/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.pk;

import com.tushana.entity.photo.*;
import com.tushana.entity.line.*;
import com.tushana.entity.Pk;
import com.zlfun.framework.db.GenericCreator;
import com.zlfun.framework.db.Transaction;
import com.zlfun.framework.db.generic.GenericCreate;
 

/**
 *
 * @author THiNk
 */
public class PkCreator extends GenericCreator<Pk> {

    public PkCreator(Transaction ts) {
        super(ts);
    }

    @Override
    public GenericCreate<Pk> newCreate() {
        return new PkCreate();
    }

}
