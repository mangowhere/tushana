package com.zlfun.framework;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zlfun.framework.config.SystemConfig;

/**
 * 系统启动的时候第一个加载的类
 *
 */
public class Boot implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent context) {

        SystemConfig.getInstance().init(context.getServletContext());

        Framework.getInstance().init("com.zulong.conf.api");
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

}
