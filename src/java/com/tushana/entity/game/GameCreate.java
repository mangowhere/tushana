/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tushana.entity.game;

 
 
import com.tushana.entity.line.*;
import com.tushana.entity.Game;
import com.zlfun.framework.db.generic.GenericCreate;
 

/**
 *
 * @author THiNk
 */
public class GameCreate extends GenericCreate<Game>{

    @Override
    public Game create() {
       return new Game();
    }
    
}
