/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.player;

import com.tushana.entity.photo.*;
import com.tushana.entity.line.*;
import com.kamike.db.generic.GenericSelect;
import com.tushana.entity.Player;
 
/**
 *
 * @author Administrator
 */
public class PlayerSelect extends GenericSelect<Player> {

    public PlayerSelect(Player t) {
        super(t);
    }

    public PlayerSelect() {
        super();
    }

    @Override
    public Player create() {
        return new Player();
    }

}
