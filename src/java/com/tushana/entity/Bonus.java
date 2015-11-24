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
@TableName("tushana_bonus")
public class Bonus {

    @FieldName("id")
    public String id;
    @FieldName("player_id")
    public String playerId;
    @FieldName("step")
    public int step;
    @FieldName("target")
    public int target;
    @FieldName("last_date")
    public Date last_date;

}
