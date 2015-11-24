/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.api;

import com.tushana.upload.PhotoResult;
import com.zlfun.framework.BinaryAction;
import com.zlfun.framework.FrameworkException;
import com.zlfun.framework.config.SystemConfig;
import com.zlfun.framework.misc.FsUtils;
import com.zlfun.framework.misc.UploadUtils;
import com.zlfun.framework.misc.UuidUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hubin
 */
public class UploadPhoto extends BinaryAction<PhotoResult> {

    @Override
    protected PhotoResult proceed(HttpServletRequest request, HttpServletResponse response) throws FrameworkException {
        byte[] buffer = UploadUtils.getFileBytes(request);
        String uuid = UuidUtils.base58Uuid();
        FsUtils.writeToDisk(SystemConfig.photoPath + uuid, buffer);
        return new PhotoResult(uuid);
    }

}
