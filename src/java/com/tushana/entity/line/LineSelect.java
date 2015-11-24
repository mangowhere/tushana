/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity.line;

import com.kamike.db.generic.GenericSelect;
import com.tushana.entity.Line;
 
/**
 *
 * @author Administrator
 */
public class LineSelect extends GenericSelect<Line> {

    public LineSelect(Line t) {
        super(t);
    }

    public LineSelect() {
        super();
    }

    @Override
    public Line create() {
        return new Line();
    }

}
