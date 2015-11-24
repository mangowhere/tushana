/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity;

import com.kamike.db.generic.FieldName;
import com.kamike.db.generic.TableName;
import java.util.Date;

/**
 *
 * @author hubin
 */
@TableName("tushana_pk")
public class Pk {

    @FieldName("id")
    public String id;

    @FieldName("left_id")
    public String left_id;

    @FieldName("left_name")
    public String left_name;

    @FieldName("left_memo")
    public String left_memo;

    @FieldName("right_id")
    public String right_id;

    @FieldName("right_name")
    public String right_name;

    @FieldName("right_memo")
    public String rightMemo;
    @FieldName("create_date")
    public Date create_date;
    @FieldName("step")
    public int step;
    @FieldName("open")
    public boolean open;
    @FieldName("left")
    public long left;
    @FieldName("right")
    public long right;
    @FieldName("win")
    public int win;

}
