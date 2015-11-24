/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana;

import com.zlfun.framework.WebEntity;
import com.zlfun.framework.WebParam;

/**
 *
 * @author mango
 */
@WebEntity 

public class GameParam {
    @WebParam("game_id")
    public String game_id;
    
    @WebParam("token")
    public String token;
    
}
