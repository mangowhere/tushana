/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.category;

import com.tushana.entity.photo.*;
import com.tushana.entity.line.*;
import com.kamike.db.generic.GenericSelect;
import com.tushana.entity.Category;
 
/**
 *
 * @author Administrator
 */
public class CategorySelect extends GenericSelect<Category> {

    public CategorySelect(Category t) {
        super(t);
    }

    public CategorySelect() {
        super();
    }

    @Override
    public Category create() {
        return new Category();
    }

}
