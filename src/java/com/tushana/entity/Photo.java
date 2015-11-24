/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity;

import com.kamike.db.generic.FieldName;
import com.kamike.db.generic.TableName;

/**
 * 作品,参赛作品
 *
 * @author hubin
 */
@TableName("tushana_photo")
public class Photo {

    @FieldName("id")
    public String id;
 
    
    @FieldName("width")
    public int width;
    @FieldName("height")
    public int height;
    @FieldName("player_id")
    public String player_id;
    @FieldName("player_name")
    public String player_name;
    @FieldName("player_memo")
    public String player_memo;
    @FieldName("title")
    public String title;
    @FieldName("game_id")
    public String game_id;
}
