/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.misc;

import java.util.Hashtable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hubin
 */
public class CookieUtils {

    public static final int SECONDS_IN_YEAR = 3600 * 24 * 365;

    public static void addCookie(HttpServletResponse response, String key, String value, int maxAge, String path,
            String domain) {
        Cookie cookie = createCookie(key, value, maxAge, path, domain);
        response.addCookie(cookie);
    }

    public static void addCookie(HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        response.addCookie(cookie);
    }

    /**
     * 读取Cookie
     *
     * @param request
     * @return Hashtable 返回cookie的键值对
     */
    public static Hashtable<String, String> getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Hashtable<String, String> cookieHt = new Hashtable<String, String>();

        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                cookieHt.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookieHt;
    }

    /**
     * 修改cookie中指定键的
     *
     * @param request
     * @param name 指定的键
     * @param value
     *
     */
    public static void setCookieValueByKey(HttpServletRequest request, HttpServletResponse response, String key,
            String value) {
        Cookie[] cookies = request.getCookies();
        if (cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (key.equalsIgnoreCase(cookie.getName())) {
                    cookie.setValue(value);
                    response.addCookie(cookie);
                    return;
                }
            }
        }
        addCookie(response, key, value);
    }

    public static void setCookieDetailByKey(HttpServletRequest request, HttpServletResponse response, String key,
            String value, int maxAge, String path, String domain) {
        Cookie[] cookies = request.getCookies();
        if (cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (key.equalsIgnoreCase(cookie.getName())) {
                    cookie.setValue(value);
                    cookie.setPath(path);
                    cookie.setMaxAge(maxAge);
                    cookie.setDomain(domain);
                    response.addCookie(cookie);
                    return;
                }
            }
        }
        addCookie(response, key, value, maxAge, path, domain);
    }

    /**
     * 得到指定键的
     *
     * @param request
     * @param name 指定的键
     * @return String
     */
    public static String getCookieValueByKey(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (key.equalsIgnoreCase(cookies[i].getName())) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    /**
     * cookie
     *
     * @param request
     * @param response
     */
    public static void deletesCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                //
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    private static Cookie createCookie(String key, String value, int maxAge, String path, String domain) {
        Cookie cookie = new Cookie(key, value);
        if (domain != null && !domain.equals("") && !domain.equals("null")) {
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        return cookie;
    }
}
