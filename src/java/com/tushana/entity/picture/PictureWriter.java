/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.picture;

import com.tushana.entity.photo.*;
import com.tushana.entity.line.*;
import com.tushana.entity.Picture;
import com.zlfun.framework.db.GenericWriter;
import com.zlfun.framework.db.Transaction;
 

/**
 *
 * @author Administrator
 */
public class PictureWriter extends GenericWriter<Picture> {

    public PictureWriter(Transaction ts) {
        super(ts);
    }

    
}
