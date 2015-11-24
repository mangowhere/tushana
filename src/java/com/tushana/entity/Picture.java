/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity;

import com.kamike.db.generic.FieldLength;
import com.kamike.db.generic.FieldName;
import com.kamike.db.generic.Id;
import com.kamike.db.generic.TableName;
import java.util.Date;

/**
 *
 * @author hubin
 */
@TableName("tushuana_picture")
public class Picture {

    @FieldName("id")
    @FieldLength(64)
    @Id
    public String id;
    @FieldName("name")
    @FieldLength(64)
    public String name;
    @FieldName("photo_id")
    @FieldLength(64)
    public String photo_id;
    @FieldName("create_date")
    public Date create_date;
    @FieldName("category")
    public String category;

}
