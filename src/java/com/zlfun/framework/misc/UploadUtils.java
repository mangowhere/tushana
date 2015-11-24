package com.zlfun.framework.misc;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

 

/**
 *
 * @author Brin
 */
public class UploadUtils {

 

    public static byte[] getFileBytes(HttpServletRequest request) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 不做迁移
        // 获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 获取文件需要上传到的路径

        // 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
        factory.setSizeThreshold(1024 * 1024);

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        try {
            // 可以上传多个文件
            List<FileItem> list = (List<FileItem>) upload.parseRequest(request);

            for (FileItem item : list) {
                // 获取表单的属性名字
                String name = item.getFieldName();

                // 如果获取的 表单信息是普通的 文本 信息
                if (item.isFormField()) {
                    // 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
                    String value = new String(item.getString().getBytes("iso-8859-1"), "utf-8");

                    request.setAttribute(name, value);
                } // 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
                else {
                    /**
                     * 以下三步，主要获取 上传文件的名字
                     */
                    // 获取路径名
                    String value = item.getName();
                    // 这里访问数据库
                    // 索引到最后一个反斜杠
                    value = java.net.URLDecoder.decode(value, "UTF-8");
                    int start = value.lastIndexOf("\\");
                    // 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
                    String filename = value.substring(start + 1);

                    InputStream in = item.getInputStream();
                    int length = 0;
                    byte[] buf = new byte[1024];
                    System.out.println("获取上传文件的总共的容量：" + item.getSize());
                    // in.read(buf) 每次读到的数据存放在 buf 数组中
                    while ((length = in.read(buf)) != -1) {
                        // 在 buf 数组中 取出数据 写到 （输出流）磁盘上
                        out.write(buf, 0, length);
                    }
                    
                    try {

                        if (in != null) {
                            in.close();
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(UploadUtils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return out.toByteArray();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Logger.getLogger(UploadUtils.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;

    }

    private static String getBrowser(HttpServletRequest request) {
        String agent = request.getHeader("USER-AGENT");
        if (agent == null) {
            return null;
        }
        String userAgent = agent.toLowerCase();

        if (userAgent.indexOf("msie") >= 0) {

            return "IE";

        } else if (userAgent.indexOf("mozilla") >= 0) {

            return "FF";

        } else if (userAgent.indexOf("applewebkit") >= 0) {

            return "CH";

        } else if (userAgent.indexOf("safari") >= 0) {

            return "SF";

        } else if (userAgent.indexOf("opera") >= 0) {

            return "OP";

        }

        return null;

    }

    public static String getContentDisposition(HttpServletRequest request, String fileName) {
        String contentDisposition = "";
        String browser = getBrowser(request);
        try {
            if ("IE".equals(browser)) {
                contentDisposition = "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            } else if ("CH".equals(browser)) {
                contentDisposition = "attachment; filename=" + MimeUtility.encodeText(fileName, "UTF8", "B");
            } else if ("SF".equals(browser)) {
                contentDisposition = "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else {
                contentDisposition = "attachment; filename*=UTF-8''"
                        + URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");

            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UploadUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contentDisposition;

    }

    // 这种下载可能会影响性能
    public static boolean download(HttpServletRequest request, HttpServletResponse response, String uuid) {

        InputStream bis;
        boolean ret = false;
        try {

            byte[] voiceData =FsUtils.readFromDisk(uuid);
            if (voiceData != null) {
                long p = 0L;
                long toLength = 0L;
                long contentLength = 0L;
                int rangeSwitch = 0; // 0,从头开始的全文下载；1,从某字节开始的下载（bytes=27000-）；2,从某字节开始到某字节结束的下载（bytes=27000-39000）
                long fileLength;
                String rangBytes = "";
                fileLength = voiceData.length;

                // get file content
                bis = new ByteArrayInputStream(voiceData);

                // tell the client to allow accept-ranges
                response.reset();
                response.setHeader("Accept-Ranges", "bytes");

                // client requests a file block download start byte
                String range = request.getHeader("Range");
                if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
                    response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
                    rangBytes = range.replaceAll("bytes=", "");
                    if (rangBytes.endsWith("-")) { // bytes=270000-
                        rangeSwitch = 1;
                        p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
                        contentLength = fileLength - p; // 客户端请求的是270000之后的字节（包括bytes下标索引为270000的字节）
                    } else { // bytes=270000-320000
                        rangeSwitch = 2;
                        String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
                        String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());
                        p = Long.parseLong(temp1);
                        toLength = Long.parseLong(temp2);
                        contentLength = toLength - p + 1; // 客户端请求的是
                        // 270000-320000
                        // 之间的字节
                    }
                } else {
                    contentLength = fileLength;
                }

                // 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
                // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
                response.setHeader("Content-Length", new Long(contentLength).toString());

                // 断点开始
                // 响应的格式是:
                // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
                if (rangeSwitch == 1) {
                    String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-").append(
                            new Long(fileLength - 1).toString()).append("/").append(new Long(fileLength).toString()).toString();
                    response.setHeader("Content-Range", contentRange);
                    bis.skip(p);
                } else if (rangeSwitch == 2) {
                    String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                    bis.skip(p);
                } else {
                    String contentRange = new StringBuffer("bytes ").append("0-").append(fileLength - 1).append("/").append(
                            fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                }

                response.setContentType("application/octet-stream");
                response.addHeader("Content-Disposition", getContentDisposition(request, uuid));

                OutputStream out = response.getOutputStream();

                int n = 0;
                long readLength = 0;
                int bsize = 1024;
                byte[] bytes = new byte[bsize];
                if (rangeSwitch == 2) {
                    // 针对 bytes=27000-39000 的请求，从27000开始写数据
                    while (readLength <= contentLength - bsize) {
                        n = bis.read(bytes);
                        readLength += n;
                        out.write(bytes, 0, n);
                    }
                    if (readLength <= contentLength) {
                        n = bis.read(bytes, 0, (int) (contentLength - readLength));
                        out.write(bytes, 0, n);
                    }
                } else {
                    while ((n = bis.read(bytes)) != -1) {
                        out.write(bytes, 0, n);
                    }
                }
                out.flush();
                out.close();
                bis.close();
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            // 忽略 ClientAbortException 之类的异常
            Logger.getLogger(UploadUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(UploadUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

}
