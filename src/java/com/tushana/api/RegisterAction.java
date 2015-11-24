/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.api;

import com.zlfun.framework.Action;
import com.zlfun.framework.FrameworkException;
import com.zlfun.framework.HtmlResult;
import com.zlfun.framework.WebAction;
import com.zlfun.framework.WebResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hubin
 */
@WebAction("register")
public class RegisterAction extends Action<WebResult> {

    

    @Override
    protected WebResult proceed(HttpServletRequest request, HttpServletResponse response)
            throws FrameworkException {
        // TODO Auto-generated method stub
       
        
        return new HtmlResult("OK");
    }

}
