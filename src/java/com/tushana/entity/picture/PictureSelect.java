/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.picture;

import com.tushana.entity.photo.*;
import com.tushana.entity.line.*;
import com.kamike.db.generic.GenericSelect;
import com.tushana.entity.Picture;
 
/**
 *
 * @author Administrator
 */
public class PictureSelect extends GenericSelect<Picture> {

    public PictureSelect(Picture t) {
        super(t);
    }

    public PictureSelect() {
        super();
    }

    @Override
    public Picture create() {
        return new Picture();
    }

}
