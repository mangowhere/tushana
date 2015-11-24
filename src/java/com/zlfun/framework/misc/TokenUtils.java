/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.misc;

import com.zlfun.framework.config.SystemConfig;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hubin
 */
public class TokenUtils {

    private static final String SECRET = "-1283cvnmcnvm,cn,.v";

    public static String genToken(String passwd, String user_name, long expire_time) {
        String content = passwd + "|" + user_name + "|" + expire_time;
        return CryptUtils.encrypt(content);
    }

    public static String genToken(String user_name, long expire_time) {
        String content = SECRET + "|" + user_name + "|" + expire_time;
        return CryptUtils.encrypt(content);
    }

    public static String genToken(String user_name) {
        String content = SECRET + "|" + user_name + "|" + System.currentTimeMillis();
        return CryptUtils.encrypt(content);
    }

    public static String getUserName(String token) {
        String userName = "";
        String raw = "";

        raw = CryptUtils.decrypt(token);

        if (raw == null || raw.indexOf('|') == -1) {
            return null;
        }
        String[] strs = raw.split("\\|");
        if (strs.length < 3) {
            return null;
        }
        userName = strs[1];
        return userName;

    }

    public static boolean validToken(String token) {
        return validToken(token, SystemConfig.sessionLifeTime * 1000);
    }

    public static boolean validToken(String token, String passwd) {
        return validToken(token, passwd, SystemConfig.sessionLifeTime * 1000);
    }

    public static boolean validToken(String token, long expireTime) {
        return validToken(token, SECRET, expireTime);
    }

    public static boolean validToken(String token, String passwd, long expireTime) {
        try {
            String raw = new String(Base58.decode(token), "UTF-8");
            if (raw.indexOf('|') == -1) {
                return false;
            }
            String[] strs = raw.split("\\|");
            if (strs.length < 3) {
                return false;
            }
            String rawKey = strs[0];
            long time = Long.parseLong(strs[2]);
            if (System.currentTimeMillis() > time + expireTime) {
                return false;
            }

            if (passwd.equals(rawKey)) {
                return true;
            }

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TokenUtils.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }
}
