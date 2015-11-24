/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.config;

import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletContext;

/**
 *
 * @author brin
 */
public class SystemConfig {

    private static volatile SystemConfig ec = new SystemConfig();

    private ConfigReader configReader;

    public static String SYSTEM_PATH = "";

    public static String sqlUser = "";
    public static String sqlPass = "";
    public static String sqlDriver = "net.ucanaccess.jdbc.UcanaccessDriver";
    public static String sqlTemplate = "jdbc:ucanaccess://";
    public static int sqlInitialPoolSize = 1;
    public static int sqlMaxStatements = 100;
    public static int sqlMinPoolSize = 1;
    public static int sqlMaxPoolSize = 1;
    public static int sqlAcquireIncrement = 0;
    public static int sqlMaxIdleTime = 3600;
    public static int sqlMaxIdleTimeExcessConnections = 1200;//快速释放链接的时间
    public static int sqlMaxConnectionAge = 27000;//正在使用的链接,多久释放
    public static int sqlMaxStatementsPerConnection = 50;
    public static int sqlNumHelperThreads = 10;
    public static int sqlIdleConnectionTestPeriod = 30;
    public static int sqlCheckoutTimeout = 30000;
    public static String sqlPreferredTestQuery = "select 1";
    public static int sqlAcquireRetryDelay = 1000;
    public static int sqlAcquireRetryAttempts = 3;
    public static String sqlFile = "c:/glassfish/rtxdb.mdb";
    public static String sqlUrl = "";

    public static int sessionLifeTime = 3600;

    public static String photoPath = "";

    private final ReentrantLock lock = new ReentrantLock();

    private SystemConfig() {

    }

    public static SystemConfig getInstance() {
        return ec;
    }

    public void init(ServletContext ctx) {
        SystemConfig.SYSTEM_PATH = ctx.getRealPath("/");

        if (SystemConfig.SYSTEM_PATH == null) {
            String webPath = this.getClass().getResource("/").getPath().replaceAll("^\\/", "");
            webPath = webPath.replaceAll("[\\\\\\/]WEB-INF[\\\\\\/]classes[\\\\\\/]?", "/");
            webPath = webPath.replaceAll("[\\\\\\/]+", "/");
            webPath = webPath.replaceAll("%20", " ");

            if (webPath.matches("^[a-zA-Z]:.*?$")) {

            } else {
                webPath = "/" + webPath;
            }

            webPath += "/";
            webPath = webPath.replaceAll("[\\\\\\/]+", "/");
            SystemConfig.SYSTEM_PATH = webPath;

        }

        lock.lock();
        try {
            this.configReader = new ConfigReader(SystemConfig.SYSTEM_PATH + "/WEB-INF/config.ini");

        } finally {
            lock.unlock();
        }

        sqlFile = this.getStringParam("sql_file", sqlFile);
        sessionLifeTime = this.getIntParam("session_life_time", sessionLifeTime);
        photoPath = this.getStringParam("photo_path", SystemConfig.SYSTEM_PATH + "/photo");
        sqlUrl = sqlTemplate + sqlFile;

    }

    /**
     * @param param
     * @param name
     * @return the systemConstants
     */
    public String getParameter(String key) {
        return this.configReader.getValue(key);
    }

    /**
     * @param param
     * @param name
     * @return the systemConstants
     */
    public int getIntParam(String key, int defaultVal) {
        String val = this.configReader.getValue(key);
        if (val != null && !val.isEmpty()) {
            try {
                return Integer.parseInt(val);
            } catch (Exception ex) {
                return defaultVal;
            }

        } else {
            return defaultVal;
        }

    }

    /**
     * @param param
     * @param name
     * @return the systemConstants
     */
    public String getStringParam(String key, String defaultVal) {
        String val = this.configReader.getValue(key);
        if (val != null && !val.isEmpty()) {
            try {
                return val;
            } catch (Exception ex) {
                return defaultVal;
            }

        } else {
            return defaultVal;
        }

    }
}
