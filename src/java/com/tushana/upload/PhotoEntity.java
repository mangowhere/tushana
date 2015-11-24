/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.upload;

import com.zlfun.framework.WebEntity;
import com.zlfun.framework.WebParam;

/**
 *
 * @author hubin
 */
@WebEntity
public class PhotoEntity {

    @WebParam("id")
    public String id;
    @WebParam("token")
    public String token;
    @WebParam("tags")
    public String tags;
     @WebParam("game_id")
    public String game_id;
  

}
