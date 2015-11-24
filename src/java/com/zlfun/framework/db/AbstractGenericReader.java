/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.db;

import java.util.ArrayList;

public abstract class AbstractGenericReader<T> {

    public abstract long count();

  

    protected AbstractGenericReader() {
        

    }

    public abstract ArrayList<T> find();

    public abstract ArrayList<T> find(T t);

    public abstract T get(T t);

}
