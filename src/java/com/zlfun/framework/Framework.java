package com.zlfun.framework;

import com.zlfun.framework.permission.PermissionInterface;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Framework {

    private Framework() {

    }

    private static Framework framework = new Framework();

    public static Framework getInstance() {
        return framework;
    }
    private PermissionInterface permissionUtils;

    public PermissionInterface permissionTool() {
        return permissionUtils;
    }

    private Map<String, Action<?>> actions = new ConcurrentHashMap<String, Action<?>>();

    public void proceed(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        StringBuilder buffer = new StringBuilder();
        String page = WebEntityUtils.getNameRestFul(request);
        Action<?> action = actions.get(page);
        if (action != null) {
            buffer.append(action.proc(request, response));

            try {
                if (action instanceof BinaryAction) {
                    
                } else {
                    PrintWriter pw = response.getWriter();
                    pw.write(buffer.toString());
                    pw.flush();
                    pw.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                response.sendError(404);
            } catch (IOException ex) {
                Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void initPermission(String permissionClassName) {
        try {
            permissionUtils = (PermissionInterface) Class.forName(permissionClassName).newInstance();
        } catch (Exception ex) {
            permissionUtils = null;
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void init(String packageName) {

        initAction(packageName);

    }

    public void init(String packageName, String permissionClassName) {
        initPermission(permissionClassName);
        initAction(packageName);

    }

    public void initAction(String packageName) {
        List<Action<?>> list = ScanUtils.findActions(packageName);
        for (Action<?> action : list) {
            String actionName;
            WebAction webAction = action.getClass().getAnnotation(WebAction.class);
            if (webAction == null || webAction.value().equals("")) {
                actionName = action.getClass().getSimpleName().toLowerCase();
            } else {
                actionName = webAction.value();
            }
            if (!actions.containsKey(actionName)) {
                actions.put(actionName, action);
            } else {
                System.out.println("exist duplicated action!" + actionName);
            }

        }
    }
}
