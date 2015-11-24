/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.permission;

/**
 *
 * @author hubin
 */
public interface PermissionInterface {

    public boolean hasPermission(String permissionId, String function);

    public boolean savePermission(Permission permission);
    public Permission getPermission(String permissionId);
}
