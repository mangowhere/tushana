/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.photo;

import com.tushana.entity.line.*;
import com.kamike.db.generic.GenericSelect;
import com.tushana.entity.Photo;
 
/**
 *
 * @author Administrator
 */
public class PhotoSelect extends GenericSelect<Photo> {

    public PhotoSelect(Photo t) {
        super(t);
    }

    public PhotoSelect() {
        super();
    }

    @Override
    public Photo create() {
        return new Photo();
    }

}
