/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana;

import com.zlfun.framework.WebResult;

/**
 *
 * @author hubin
 */
public class TushanaException extends Exception {

    public static final int SUCCESS = 0;
    public static final int COMMON_FAIL = 1;
    public static final int SERVER_FAIL = 2;
    public static final int USER_EMPTY = 3;
    public static final int TOKEN_ERROR = 4;
    public static final int NULL_ERROR = 5;
    public static final int NOT_FOUND_SESSION=6;
    public static final int NOT_FOUND_REVIEWER=7;
    /**
     *
     */
    private static final long serialVersionUID = 8276170831656932018L;

    private int retcode;

    public TushanaException() {
        retcode = 1;
    }

    public TushanaException(int errorCode) {
        retcode = errorCode;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    /**
     * @return the retcode
     */
    public int getRetcode() {
        return retcode;
    }

    

}
