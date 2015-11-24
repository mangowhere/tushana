/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.api;

import com.tushana.api.login.LoginContext;
import com.tushana.api.login.LoginUtils;
import com.tushana.entity.Reviewer;
import com.tushana.join.JoinUtils;
import com.tushana.join.NotFoundReviewerException;
import com.tushana.join.ReviewerUtils;
import com.tushana.session.NotFoundSessionException;
import com.tushana.session.Session;
import com.tushana.session.SessionInstance;
import com.tushana.upload.PhotoEntity;
import com.zlfun.framework.EntityAction;
import com.zlfun.framework.FrameworkException;
import com.zlfun.framework.WebResult;
import com.zlfun.framework.misc.TokenUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hubin
 */
public class SubmitPhoto extends EntityAction<PhotoEntity, WebResult> {

    private static final LoginUtils utils = LoginContext.getContext().utils();

    @Override
    public WebResult proceed(PhotoEntity in) throws FrameworkException {
        try {
            if (TokenUtils.validToken(in.token)) {
                String userName = TokenUtils.getUserName(in.token);
                Session session = SessionInstance.getInstance().get(userName);
                Reviewer reviewer = ReviewerUtils.getReviewerByUserId(session.userId);
                JoinUtils.addPhoto(session.userId, reviewer.name, in.game_id, "", in.id, in.tags);
            }

        } catch (NotFoundSessionException ex) {
            Logger.getLogger(SubmitPhoto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFoundReviewerException ex) {
            Logger.getLogger(SubmitPhoto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new WebResult();
    }

    @Override
    public PhotoEntity createEntity() {
        return new PhotoEntity();
    }

}
