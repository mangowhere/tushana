/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.api.login.mysql;

import com.tushana.api.login.LoginUtils;
import com.tushana.entity.Reviewer;
import com.tushana.entity.reviewer.ReviewerReader;

/**
 *
 * @author hubin
 */
public class MySQLLoginUtils extends LoginUtils {

    private static final ReviewerReader reviewerReader = new ReviewerReader();

    @Override
    public Reviewer fetchByLogin(Reviewer reviewer) {
        return reviewerReader.getByName(reviewer.name);
    }

    @Override
    public boolean isLogin(Reviewer reviewer, Reviewer existReviewer) {
       if(reviewer==null||existReviewer==null||reviewer.name==null||existReviewer.name==null)
       {
           return false;
       }
       if(reviewer.passwd.equals(existReviewer.passwd)||reviewer.device.equals(existReviewer.device))
       {
           return true;
       }
       else
       {
           return false;
       }
    }

}
