/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tushana.entity.pk;

 
 
import com.tushana.entity.photo.*;
import com.tushana.entity.line.*;
import com.tushana.entity.Pk;
import com.zlfun.framework.db.generic.GenericCreate;
 

/**
 *
 * @author THiNk
 */
public class PkCreate extends GenericCreate<Pk>{

    @Override
    public Pk create() {
       return new Pk();
    }
    
}
