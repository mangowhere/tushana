/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.category;

import com.tushana.entity.photo.*;
import com.tushana.entity.line.*;
import com.tushana.entity.Category;
import com.zlfun.framework.db.GenericWriter;
import com.zlfun.framework.db.Transaction;
 

/**
 *
 * @author Administrator
 */
public class CategoryWriter extends GenericWriter<Category> {

    public CategoryWriter(Transaction ts) {
        super(ts);
    }

    
}
