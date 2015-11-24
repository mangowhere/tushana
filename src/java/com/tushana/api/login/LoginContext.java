/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.api.login;

import com.tushana.api.login.mysql.MySQLLoginUtils;

/**
 *
 * @author hubin
 */
public class LoginContext {

    private static final LoginContext context = new LoginContext();
    private final LoginUtils utils;

    private LoginContext() {
        utils = new MySQLLoginUtils();
    }

    ;
    
    public static LoginContext getContext() {
        return context;
    }
    public LoginUtils utils()
    {
        return utils;
    }
    
}
