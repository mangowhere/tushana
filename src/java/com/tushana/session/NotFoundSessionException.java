/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.session;

import com.tushana.TushanaException;

/**
 *
 * @author hubin
 */
public class NotFoundSessionException extends TushanaException{
    public NotFoundSessionException()
    {
        super(TushanaException.NOT_FOUND_SESSION);
    }
}
