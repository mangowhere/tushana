/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.category;

import com.tushana.entity.photo.*;
import com.tushana.entity.line.*;
import com.tushana.entity.Category;
import com.zlfun.framework.db.GenericCreator;
import com.zlfun.framework.db.Transaction;
import com.zlfun.framework.db.generic.GenericCreate;
 

/**
 *
 * @author THiNk
 */
public class CategoryCreator extends GenericCreator<Category> {

    public CategoryCreator(Transaction ts) {
        super(ts);
    }

    @Override
    public GenericCreate<Category> newCreate() {
        return new CategoryCreate();
    }

}
