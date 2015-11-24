/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.api.login;

import com.zlfun.framework.WebEntity;
import com.zlfun.framework.WebResult;

/**
 *
 * @author hubin
 */
@WebEntity
public class LoginSuccessResult extends WebResult {

    public LoginSuccessResult(String token) {
        super(0,"login success ",token);
    }
}
