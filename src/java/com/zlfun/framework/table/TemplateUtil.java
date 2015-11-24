/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author THiNk
 */ 
public class TemplateUtil {

    private static final Pattern pattern = Pattern.compile("\\$\\{([_A-Za-z0-9]*)\\}");

    public static String readFile(String fullFileName) {
        File file = new File(fullFileName);
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            // 按照utf-8的字符编码的格式进行扫描处理，若该文件的编码不是utf-8的编码方式则扫描出来的结果将0,也就是扫描不出来  
            // 假设test.txt的文件编码格式是gbk的编码方式，若按照utf-8的编码方式进行扫描的话，则扫描结果为0  
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
                 buffer.append("\n");
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block  

        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }

    public static List<TemplateVariable> getVariables(String content) {
        List<TemplateVariable> vars = new ArrayList<TemplateVariable>();

        Matcher m = pattern.matcher(content);

        while (m.find()) {

            TemplateVariable var = new TemplateVariable();
            var.setName(m.group(1));
            String target = m.group(0);
          
            var.setPattern(target);
            var.setValue(target);

            if (notExist(vars, var.getName())) {
                vars.add(var);
            }
        }
        return vars;

    }

    public static boolean notExist(List<TemplateVariable> vars, String name) {

        for (TemplateVariable var : vars) {
            if (var.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public static void setValue(List<TemplateVariable> vars, String name, String value) {
        if (name == null || value == null) {
            return;
        }
        for (TemplateVariable var : vars) {
            if (var.getName().equals(name)) {
                var.setValue(value);
                break;
            }
        }
    }
    //字符串复制了
    public static String getResult(List<TemplateVariable> vars, String content) {
        for (TemplateVariable var : vars) {
            if (!var.getPattern().equals(var.getValue()) || var.getValue() != null) {
                content = content.replace(var.getPattern(), var.getValue());
            }
        }
        return content;
    }

}
