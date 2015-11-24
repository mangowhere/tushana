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
 * 一次比赛的所有参加人员对应的关系
 *
 * @author hubin
 */
@TableName("tushana_line")
public class Line {

    @FieldName("id")
    public String id;
    @FieldName("player_id")
    public String player_id;
    @FieldName("player_name")
    public String player_name;
    @FieldName("game_id")
    public String game_id;
    @FieldName("create_date")
    public Date create_date;

    @FieldName("memo")
    public String memo;

    @FieldName("rank")
    public int rank;

    
}
