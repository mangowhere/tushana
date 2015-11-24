/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.game;

import com.tushana.entity.line.*;
import com.kamike.db.generic.GenericSelect;
import com.tushana.entity.Game;
 
/**
 *
 * @author Administrator
 */
public class GameSelect extends GenericSelect<Game> {

    public GameSelect(Game t) {
        super(t);
    }

    public GameSelect() {
        super();
    }

    @Override
    public Game create() {
        return new Game();
    }

}
