/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.permission;

import com.zlfun.framework.misc.TokenUtils;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author hubin
 */
public class Permission {

    private final String id;

    private Set<String> functions;

    public Permission(Set<String> functions) {
        Iterator<String> iter = functions.iterator();
        StringBuilder buffer = new StringBuilder();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            buffer.append(":");
        }
        this.id = TokenUtils.genToken(buffer.toString());
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the functions
     */
    public Set<String> getFunctions() {
        return functions;
    }

}
