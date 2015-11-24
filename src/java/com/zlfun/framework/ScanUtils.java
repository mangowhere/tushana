package com.zlfun.framework;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ScanUtils {

    public static List<Class<?>> findClasses(String packageName) {
        String packageDirName = packageName.replace('.', '/');

        List<Class<?>> classes = new ArrayList<Class<?>>();

        Enumeration<URL> dirs;

        try {
            dirs = Thread.currentThread().getContextClassLoader()
                    .getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");

                    File dir = new File(filePath);
                    if (!dir.exists() || !dir.isDirectory()) {
                        continue;
                    }
                    File[] files = dir.listFiles(new FilenameFilter() {

                        @Override
                        public boolean accept(File file, String name) {
                            // TODO Auto-generated method stub
                            return name.endsWith(".class");
                        }

                    });
                    for (File file : files) {
                        if (file.isDirectory()) {
                            continue;
                        }
                        String className = file.getName().substring(0,
                                file.getName().length() - 6);
                        classes.add(Thread.currentThread()
                                .getContextClassLoader()
                                .loadClass(packageName + "." + className));
                    }
                } else {

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return classes;
    }
    

    public static List<Action<?>> findActions(String packageName) {
        List<Class<?>> classes = findClasses(packageName);
        List<Action<?>> actions = new ArrayList<Action<?>>();
        for (Class<?> cls : classes) {
            if (Action.class.isAssignableFrom(cls)) {

                try {
                    actions.add((Action<?>) cls.newInstance());
                } catch (InstantiationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return actions;
    }

}
