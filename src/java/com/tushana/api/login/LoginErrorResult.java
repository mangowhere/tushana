/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.api.login;

import com.zlfun.framework.WebResult;

/**
 *
 * @author hubin
 */
public class LoginErrorResult extends WebResult {

    public LoginErrorResult() {
        super(1, "login error ");
    }
}
