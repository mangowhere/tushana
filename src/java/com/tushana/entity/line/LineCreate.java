/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tushana.entity.line;

 
 
import com.tushana.entity.Line;
import com.zlfun.framework.db.generic.GenericCreate;
 

/**
 *
 * @author THiNk
 */
public class LineCreate extends GenericCreate<Line>{

    @Override
    public Line create() {
       return new Line();
    }
    
}
