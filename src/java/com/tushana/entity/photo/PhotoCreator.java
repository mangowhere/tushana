/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.photo;

import com.tushana.entity.line.*;
import com.tushana.entity.Photo;
import com.zlfun.framework.db.GenericCreator;
import com.zlfun.framework.db.Transaction;
import com.zlfun.framework.db.generic.GenericCreate;
 

/**
 *
 * @author THiNk
 */
public class PhotoCreator extends GenericCreator<Photo> {

    public PhotoCreator(Transaction ts) {
        super(ts);
    }

    @Override
    public GenericCreate<Photo> newCreate() {
        return new PhotoCreate();
    }

}
