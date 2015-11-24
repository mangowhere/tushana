package com.zlfun.framework.misc;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

 
import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;

/**
 *
 * @author THiNk
 */
public class PinyinUtils {

    public static String getShortPinyin(String str) {
        if(str==null)
            return "";
        return PinyinHelper.getShortPinyin(str);
    }
      public static String getPinyin(String str) {
        if(str==null)
            return "";
        return PinyinHelper.convertToPinyinString(str,"",PinyinFormat.WITHOUT_TONE);
    }

}
