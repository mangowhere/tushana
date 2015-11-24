/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.table;

import com.zlfun.framework.MiscDateUtils;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author hubin
 */
public class ItemComparator<T> implements Comparator<T> {

    private String name;
    private boolean asc;

    public ItemComparator(String name, boolean asc) {
        this.name = name;
        this.asc = asc;
    }

    private boolean isNumeric(String var) {
        if (var == null && var.equals("")) {
            return false;
        }
        if (var.charAt(0) == '-') {
            return StringUtils.isNumeric(var.substring(1));
        } else {
            return StringUtils.isNumeric(var);
        }
    }

    @Override
    public int compare(T o1, T o2) {

        Map<String, String> o1m = TableUtils.genItemFieldMap(o1);
        Map<String, String> o2m = TableUtils.genItemFieldMap(o2);

        String var1 = o1m.get(name);
        String var2 = o2m.get(name);
        if (isNumeric(var1) && isNumeric(var2)) {
            if (asc) {
                return Double.compare(Double.parseDouble(var1), Double.parseDouble(var2));

            } else {
                return Double.compare(Double.parseDouble(var2), Double.parseDouble(var1));
            }
        } else {
            if (asc) {
                return var1.compareTo(var2);
            } else {
                return var2.compareTo(var1);
            }
        }

    }

}
