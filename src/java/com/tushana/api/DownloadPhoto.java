/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.api;

import com.tushana.upload.PhotoEntity;
import com.zlfun.framework.BinaryAction;
import com.zlfun.framework.FrameworkException;
import com.zlfun.framework.misc.UploadUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hubin
 */
public class DownloadPhoto extends BinaryAction<PhotoEntity> {

    @Override
    protected PhotoEntity proceed(HttpServletRequest request, HttpServletResponse response) throws FrameworkException {
        String id = request.getParameter("id");
        UploadUtils.download(request, response, id);
        return new PhotoEntity();
    }

}
