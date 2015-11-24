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
@TableName("tushana_game")
public class Game {

    @FieldName("id")
    public String id;
    @FieldName("name")
    public String name;
    @FieldName("memo")
    public String memo;
    @FieldName("create_date")
    public Date create_date;
   @FieldName("turn")
    public int turn;
    
}
