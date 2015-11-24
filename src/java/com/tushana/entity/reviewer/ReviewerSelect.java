/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.reviewer;

import com.tushana.entity.pk.*;
import com.tushana.entity.photo.*;
import com.tushana.entity.line.*;
import com.kamike.db.generic.GenericSelect;
import com.tushana.entity.Reviewer;
 
/**
 *
 * @author Administrator
 */
public class ReviewerSelect extends GenericSelect<Reviewer> {

    public ReviewerSelect(Reviewer t) {
        super(t);
    }

    public ReviewerSelect() {
        super();
    }

    @Override
    public Reviewer create() {
        return new Reviewer();
    }

}
