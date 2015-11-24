/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.excel;

import com.zlfun.framework.MiscDateUtils;
import com.zlfun.framework.WebParam;
import com.zlfun.framework.table.ItemField;
import com.zlfun.framework.table.TableUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Administrator
 */
public class ExcelUtils {

    private static String timestampPattern = "yyyy-MM-dd HH:mm:ss";
    private static String datePattern = "yyyy-MM-dd";

    public static <T> void write(String sheetName, Class<T> clazz, List<T> list, OutputStream out) {
        if (out == null) {
            return;
        }
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);

        Drawing drawing = sheet.createDrawingPatriarch();

        List<String> header = buildHeader(workbook, sheet, clazz);

        buildBody(workbook, sheet, drawing, header, list);

        try {
            workbook.write(out);
        } catch (IOException ex) {
            // TODO Auto-generated catch block
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static String escape(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        escape(s, sb);
        return sb.toString();
    }

    private static void escape(String s, StringBuilder sb) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    if ((ch >= '\u0000' && ch <= '\u001F') || (ch >= '\u007F' && ch <= '\u009F') || (ch >= '\u2000' && ch <= '\u20FF')) {
                        String str = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int k = 0; k < 4 - str.length(); k++) {
                            sb.append('0');
                        }
                        sb.append(str.toUpperCase());
                    } else {
                        sb.append(ch);
                    }
            }
        }
    }

    public static String objectToStr(Object value) {
        if (value == null) {
            return "null";
        }

        if (value instanceof String) {
            return escape((String) value);
        }

        if (value instanceof Double) {
            if (((Double) value).isInfinite() || ((Double) value).isNaN()) {
                return "null";
            } else {
                return value.toString();
            }
        }

        if (value instanceof Float) {
            if (((Float) value).isInfinite() || ((Float) value).isNaN()) {
                return "null";
            } else {
                return value.toString();
            }
        }

        if (value instanceof Number) {
            return value.toString();
        }

        if (value instanceof Boolean) {
            return value.toString();
        }

        if (value instanceof java.util.Date) {
            if (value instanceof java.sql.Timestamp) {
                return "\"" + new SimpleDateFormat(timestampPattern).format(value) + "\"";
            }
            if (value instanceof java.sql.Time) {
                return "\"" + value.toString() + "\"";
            }
            return "\"" + new SimpleDateFormat(datePattern).format(value) + "\"";
        }

        return "";

    }

    public static <T> void buildBody(Workbook workbook, Sheet sheet, Drawing drawing, List<String> header, List<T> list) {

        CellStyle bodyStyle = createBodyStyle(workbook);

        for (short i = 0; i < list.size(); i++) {
            T t = list.get(i);

            Row row = sheet.createRow(i+1);
            Map<String, String> map = genWebParamMap(t);
            short j = 0;
            for (String entry : header) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(bodyStyle);
                if (map.containsKey(entry)) {
                    cell.setCellValue(map.get(entry));
                } else {
                    cell.setCellValue("");
                }
                j++;
            }

        }

    }

    public static <T> List<String> buildHeader(Workbook workbook, Sheet sheet, Class<T> clazz) {
        // 产生表格标题行
        Row row = sheet.createRow(0);
        CellStyle headerStyle = createHeaderStyle(workbook);
        List<String> columns = genItemFieldHeader(clazz);

        for (short i = 0; i < columns.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(columns.get(i));
        }
        return columns;
    }

    public static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        // 生成一个字体
        Font font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        return style;
    }

    public static CellStyle createBodyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style.setFont(font);
        return style;
    }

    private static <T> Map<String, String> genRowMap(Row row, List<String> cols, FormulaEvaluator evaluator) {
        Map<String, String> map = new HashMap<String, String>();
        int j = 0;
        for (String col : cols) {
            Cell cell = row.getCell(j);// 获取单元格对象
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    map.put(col, String.valueOf(cell.getBooleanCellValue()));
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    map.put(col, String.valueOf(cell.getNumericCellValue()));

                    break;
                case Cell.CELL_TYPE_STRING:

                    map.put(col, String.valueOf(cell.getStringCellValue()));
                    break;
                case Cell.CELL_TYPE_BLANK:
                    map.put(col, String.valueOf(""));

                    break;
                case Cell.CELL_TYPE_ERROR:
                    map.put(col, String.valueOf("error"));
                    break;

                // CELL_TYPE_FORMULA will never occur  
                case Cell.CELL_TYPE_FORMULA:

                    evaluator.evaluateFormulaCell(cell);
                    map.put(col, String.valueOf(cell.getNumericCellValue()));
                    break;

            }
            j++;
        }

        return map;
    }

    private static <T> void fill(Class<T> clazz, List<T> result, String fileName, InputStream is) {

        try {
            Workbook excel = null;
            if (fileName.indexOf(".xlsx") > 0) {
                excel = new XSSFWorkbook(is);// 创建Excel2007文件对象
            } else if (fileName.indexOf(".xls") > 0) {
                excel = new HSSFWorkbook(is);// 创建Excel2003文件对象

            } else {
                return;
            }
            FormulaEvaluator evaluator = excel.getCreationHelper().createFormulaEvaluator();
            Sheet sheet = excel.getSheetAt(0);// 取出第一个工作表，索引是0
            // 开始循环遍历行，表头不处理，从1开始
            List<String> header = new ArrayList<String>();
            if (sheet.getLastRowNum() >= 0) {
                Row row = sheet.getRow(0);// 获取表头对象

                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);// 获取单元格对象

                    if (cell != null) {// 单元格为空设置cellStr为空串

                        header.add(cell.getStringCellValue());
                    }

                }

            }

            //正文
            if (sheet.getLastRowNum() > 1) {

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                    Row row = sheet.getRow(i);// 获取行对象
                    if (row == null) {// 如果为空，不处理
                        continue;
                    }
                    Map<String, String> map = genRowMap(row, header, evaluator);
                    T t = fill(map, clazz.newInstance());
                    result.add(t);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ExcelUtils.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {// 关闭文件流
            if (is != null) {
                try {
                    is.close();

                } catch (IOException ex) {
                    Logger.getLogger(ExcelUtils.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return;
    }

    public static <T> List<T> read(String fileName, Class<T> clazz) {
        List<T> result = new ArrayList<T>();

        try {

            //获取路径名  
            FileInputStream fis = new FileInputStream(fileName);

            fill(clazz, result, fileName, fis);

        } catch (FileNotFoundException ex) {

            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static <T> List<T> httpInput(HttpServletRequest request, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        //获得磁盘文件条目工厂  
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //获取文件需要上传到的路径  
        String path = request.getSession().getServletContext().getRealPath("/");
        factory.setRepository(new File(path));
        //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室  
        factory.setSizeThreshold(1024 * 1024);
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            //可以上传多个文件  
            List<FileItem> list = (List<FileItem>) upload.parseRequest(request);

            for (FileItem item : list) {
                //获取表单的属性名字  
                String name = item.getFieldName();

                //如果获取的 表单信息是普通的 文本 信息  
                if (item.isFormField()) {
                    //获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的  
                    String value = item.getString();

                    request.setAttribute(name, value);
                } //对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些  
                else {
                    /**
                     * 以下三步，主要获取 上传文件的名字
                     */
                    //获取路径名  
                    String value = item.getName();
                    //索引到最后一个反斜杠  

                    fill(clazz, result, value, item.getInputStream());

                    //下面再去干什么呢，入库
                }
            }

        } catch (FileUploadException ex) {
            // TODO Auto-generated catch block      excel=null;
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {

            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static <T> void httpOutput(String name, Class<T> clazz, List<T> list, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");//设置request的编码方式，防止中文乱码
            String fileName = name;//设置导出的文件名
            String contentType = "application/vnd.ms-excel";//定义导出文件的格式的字符串
            String recommendedName = new String(fileName.getBytes(), "iso_8859_1");//设置文件名称的编码格式
            response.setContentType(contentType);//设置导出文件格式
            response.setHeader("Content-Disposition", "attachment; filename=" + recommendedName + "\"");//
            response.resetBuffer();
            //利用输出输入流导出文件
            ServletOutputStream sos = response.getOutputStream();
            write(name, clazz, list, sos);
            sos.flush();
            sos.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static <T> void save(String fileName, String sheetName, Class<T> clazz, List<T> list) {
        try {
            //利用输出输入流导出文件
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(fileName));
            write(sheetName, clazz, list, os);
            os.flush();
            os.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static <T> List<String> genItemFieldHeader(Class<T> clazz) {

        Set<String> set = new HashSet<String>();
        List<String> result = new ArrayList<String>();
        try {
            Field[] fs = clazz.getDeclaredFields();
            for (Field f : fs) {

                WebParam itemField = f.getAnnotation(WebParam.class);
                if (itemField == null) {
                    continue;
                }
                if ("".equals(itemField.value())) {
                    String fname = f.getName();
                    if (!set.contains(fname)) {
                        set.add(fname);
                        result.add(fname);
                    }
                } else {
                    String fname = itemField.value();
                    if (!set.contains(fname)) {
                        set.add(fname);
                        result.add(fname);
                    }
                }
                // 处理父类的
                Class<?> parent = clazz.getSuperclass();
                while (parent != Object.class) {
                    parent(parent, set, result);
                    parent = parent.getSuperclass();
                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    private static <T> void parent(Class<T> parentClass, Set<String> set, List<String> result) {
        Field[] fs = parentClass.getDeclaredFields();

        for (Field f : fs) {

            WebParam itemField = f.getAnnotation(WebParam.class);
            if (itemField == null) {
                continue;
            }
            if ("".equals(itemField.value())) {
                String fname = f.getName();
                if (!set.contains(fname)) {
                    set.add(fname);
                    result.add(fname);
                }
            } else {
                String fname = itemField.value();
                if (!set.contains(fname)) {
                    set.add(fname);
                    result.add(fname);
                }
            }

        }
    }

    private static <T> T fill(Map<String, String> map, T t) {
        T bean = t;
        try {

            if (t.getClass().getAnnotation(WebParam.class) == null) {

                Field[] fs = t.getClass().getDeclaredFields();
                for (Field f : fs) {

                    set(bean, f, map.get(f.getName()));

                }
            } else {
                Field[] fs = t.getClass().getDeclaredFields();
                for (Field f : fs) {
                    WebParam param = f.getAnnotation(WebParam.class);
                    if (param != null) {
                        String fname;
                        if ("".equals(param.value())) {
                            fname = f.getName();
                        } else {
                            fname = param.value();
                        }

                        set(bean, f, map.get(fname));
                    }

                }

                // 处理父类的
                Class<?> parent = t.getClass().getSuperclass();
                while (parent != Object.class) {

                    if (parent.getAnnotation(ItemField.class) != null) {

                        Field[] pfs = parent.getDeclaredFields();
                        for (Field f : pfs) {
                            WebParam param = f.getAnnotation(WebParam.class);
                            if (param != null) {
                                String fname;
                                if ("".equals(param.value())) {
                                    fname = f.getName();
                                } else {
                                    fname = param.value();
                                }

                                set(bean, f, map.get(fname));
                            }

                        }

                        parent = parent.getSuperclass();
                    } else {
                        break;
                    }

                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bean;
    }

    private static void set(Object obj, Field field, String value) {

        if (value == null) {
            return;
        }
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            Object val = value;

            if (field.getType() == int.class) {
                try {
                    val = Integer.parseInt(value);
                } catch (Exception ex) {
                    val = -1;
                }
            } else if (field.getType() == long.class) {
                try {
                    val = Long.parseLong(value);
                } catch (Exception ex) {
                    val = -1l;
                }

            } else if (field.getType() == double.class) {
                val = Double.parseDouble(value);
            } else if (field.getType() == byte.class) {
                val = Byte.parseByte(value);
            } else if (field.getType() == boolean.class) {
                val = Boolean.parseBoolean(value);
            }

            field.set(obj, val);

        } catch (Exception e) {

        }
    }
    
    
      public static <T> Map<String, String> genWebParamMap(T t) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            Field[] fs = t.getClass().getDeclaredFields();
            for (Field f : fs) {
                String data = get(t, f);
                if (data == null) {
                    continue;
                }
                WebParam itemField = f.getAnnotation(WebParam.class);
                if (itemField == null) {
                    continue;
                }
                if ("".equals(itemField.value())) {
                    String fname = f.getName();
                    if (!map.containsKey(fname)) {
                        map.put(fname, data);
                    }
                } else {
                    String fname = itemField.value();
                    if (!map.containsKey(fname)) {
                        map.put(fname, data);
                    }
                }
                // 处理父类的
                Class<?> parent = t.getClass().getSuperclass();
                while (parent != Object.class) {
                    parent(t, parent, map);
                    parent = parent.getSuperclass();
                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return map;
    }

    private static <T> void parent(T t, Class<?> parentClass, Map<String, String> map) {
        Field[] fs = parentClass.getDeclaredFields();

        for (Field f : fs) {
            String data = get(t, f);
            if (data == null) {
                continue;
            }
            WebParam itemField = f.getAnnotation(WebParam.class);
            if (itemField == null) {
                continue;
            }
            if ("".equals(itemField.value())) {
                String fname = f.getName();
                if (!map.containsKey(fname)) {
                    map.put(fname, data);
                }
            } else {
                String fname = itemField.value();
                if (!map.containsKey(fname)) {
                    map.put(fname, data);
                }
            }

        }
    }

    private static String get(Object obj, Field field) {

        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            if (field.getType() == int.class) {
                return String.valueOf(field.getInt(obj));

            } else if (field.getType() == long.class) {
                return String.valueOf(field.getLong(obj));

            } else if (field.getType() == double.class) {
                return String.valueOf(field.getDouble(obj));
            } else if (field.getType() == byte.class) {
                return String.valueOf(field.getByte(obj));
            } else if (field.getType() == boolean.class) {
                return String.valueOf(field.getBoolean(obj));
            } else if (field.getType() == String.class) {
                if (field.get(obj) == null) {
                    return "";
                }
                return String.valueOf(field.get(obj));
            } else if (field.getType() == Date.class) {
                if (field.get(obj) == null) {
                    return "";
                }
                return MiscDateUtils.getDateTime((Date) field.get(obj));
            }

        } catch (Exception e) {

        }
        return "";
    }
}
