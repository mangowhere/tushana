/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana;

/**
 *
 * @author hubin
 */
public class CommonErrorException extends TushanaException{
    public CommonErrorException()
    {
        super(TushanaException.COMMON_FAIL);
    }
    
}
