/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tushana.entity.reviewer;

 
 
import com.tushana.entity.Reviewer;
import com.zlfun.framework.db.generic.GenericCreate;
 

/**
 *
 * @author THiNk
 */
public class ReviewerCreate extends GenericCreate<Reviewer>{

    @Override
    public Reviewer create() {
       return new Reviewer();
    }
    
}
