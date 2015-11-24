/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.api;

import com.tushana.GameParam;
import com.tushana.api.login.LoginContext;
import com.tushana.api.login.LoginSuccessResult;
import com.tushana.api.login.LoginUtils;
import com.tushana.entity.Reviewer;
import com.tushana.session.Session;
import com.tushana.session.SessionInstance;
import com.zlfun.framework.EntityAction;
import com.zlfun.framework.FrameworkException;
import com.zlfun.framework.HtmlResult;
import com.zlfun.framework.WebAction;
import com.zlfun.framework.WebResult;

/**
 *
 * @author mango
 */
@WebAction("pk_list")
public class PkList extends EntityAction<GameParam, HtmlResult> {

    private static final LoginUtils utils = LoginContext.getContext().utils();

    @Override
    public HtmlResult proceed(GameParam in) throws FrameworkException {
        Reviewer existReviewer = utils.fetchByLogin(in);
        if (utils.isLogin(in, existReviewer)) {
            //组装显示模板
            Session session = existReviewer.toSession();
            SessionInstance.getInstance().addSession(existReviewer.toSession());
            return new LoginSuccessResult(session.token);
        } else {
            return new WebResult(1, "login error");
        }

    }

    @Override
    public Reviewer createEntity() {
        return new Reviewer();
    }

}
