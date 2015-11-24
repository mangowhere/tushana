/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hubin
 */
public class SessionInstance {

    private static SessionInstance inst = new SessionInstance();
    public static final int maxSessionNum = 10000;

    private SessionInstance() {

    }

    public static SessionInstance getInstance() {
        return inst;
    }
    public Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    public void addSession(Session session) {
        this.sessionMap.put(session.userId, session);
    }

    public Session get(String userId) throws NotFoundSessionException {

        if (sessionMap.containsKey(userId)) {
            return sessionMap.get(userId);
        } else {
            throw new NotFoundSessionException();
        }
    }

}
