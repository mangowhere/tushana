/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity;

import com.kamike.db.generic.FieldName;
import com.kamike.db.generic.Id;
import com.kamike.db.generic.TableName;
import java.util.Date;

/**
 *
 * @author hubin
 */
@TableName("tushana_player")
public class Player {
    @FieldName("id")
    @Id
    public String id;
    @FieldName("user_id")
    public String user_id;
    @FieldName("name")
    public String name;
    @FieldName("game_id")
    public String game_id;
    @FieldName("picture_id")
    public String picture_id;
    @FieldName("head_image_id")
    public String head_image_id;
    @FieldName("turn")
    public int turn;
    @FieldName("join_time")
    public Date join_time;
    
}