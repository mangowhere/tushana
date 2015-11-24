/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity;

import com.kamike.db.generic.FieldName;
import com.kamike.db.generic.TableName;
import com.tushana.session.Session;
import com.zlfun.framework.WebEntity;
import com.zlfun.framework.WebParam;
import com.zlfun.framework.misc.TokenUtils;
import java.util.Date;

/**
 * 评委
 *
 * @author hubin
 */
@TableName("tushana_reviewer")
@WebEntity
public class Reviewer {

    @FieldName("id")
    public String id;
    @FieldName("name")
    @WebParam
    public String name;
    @FieldName("nick_name")
    public String nick_dame;
    @FieldName("passwd")
    @WebParam
    public String passwd;
    @FieldName("device")
    public String device;
    @FieldName("email")
    public String email;
    @FieldName("telephone")
    public String telephone;
    @FieldName("level")
    public int level;
    @FieldName("experience")
    public long experience;
    @FieldName("score")
    public int score;
    @FieldName("create_date")
    public Date create_date;
    @FieldName("last_date")
    public Date last_date;

    public Session toSession() {
        Session session = new Session();
        session.name = name;
        session.token = TokenUtils.genToken(name);
        session.userId = id;
        session.valid = true;
        return session;
    }

}
