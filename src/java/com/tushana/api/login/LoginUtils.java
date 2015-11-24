/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.api.login;

import com.tushana.entity.Reviewer;
import com.zlfun.framework.WebResult;

/**
 *
 * @author hubin
 */
public abstract class LoginUtils {

    public abstract Reviewer fetchByLogin(Reviewer reviewer);

    public abstract boolean isLogin(Reviewer reviewer, Reviewer existReviewer);

     
}
