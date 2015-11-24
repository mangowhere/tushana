/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.pk;

import com.tushana.entity.photo.*;
import com.tushana.entity.line.*;
import com.kamike.db.generic.GenericSelect;
import com.tushana.entity.Pk;
 
/**
 *
 * @author Administrator
 */
public class PkSelect extends GenericSelect<Pk> {

    public PkSelect(Pk t) {
        super(t);
    }

    public PkSelect() {
        super();
    }

    @Override
    public Pk create() {
        return new Pk();
    }

}
