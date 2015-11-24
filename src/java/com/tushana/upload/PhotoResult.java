/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.upload;

import com.zlfun.framework.WebResult;

/**
 *
 * @author hubin
 */

public class PhotoResult extends WebResult {

    public String photo_id;

    public PhotoResult(String photo_id) {
        this.photo_id = photo_id;
    }
}
