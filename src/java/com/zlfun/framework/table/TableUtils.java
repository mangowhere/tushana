/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.table;

import com.zlfun.framework.MiscDateUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hubin
 */
public class TableUtils {

    public static <T> String genDetail(T t) {
        
        Detail detail = t.getClass().getAnnotation(Detail.class);
        if (detail != null) {
            return applyTemplate(t, detail.value());
        } else {
            return "";
        }

    }

    public static <T> String wrap(TableRequest request, List<T> list, Class<? extends T> clazz) {
        List<T> result = new ArrayList<T>();
        for (T t : list) {
            Map<String, String> map = TableUtils.genItemFieldMap(t);
            if (request.getSearch() != null && !request.getSearch().equals("")) {
                for (String value : map.values()) {
                    if (value.contains(request.getSearch())) {
                        result.add(t);
                    }
                }
            } else {

                if (map.containsKey(request.getFilter())) {
                    String value = map.get(request.getFilter());
                    if (value != null && value.contains(request.getValue())) {
                        result.add(t);
                    }
                }
            }
        }
        //再排序
        if (request.getSort() != null && !request.getSort().equals("")) {
            Collections.sort(result, new ItemComparator(request.getSort(), request.isAsc()));
        }
        return TableUtils.genTableWithPager(request.getOffset(), request.getSize(), result, clazz);

    }

    public static <T> String genTable(List<T> list, Class<? extends T> t) {
        StringBuilder buffer = new StringBuilder();
        try {
            HeaderTemplate headerTemplate = t.getClass().getAnnotation(HeaderTemplate.class);
            if (headerTemplate != null) {
                buffer.append(headerTemplate.value());
            }
            for (int i = 0; i < list.size(); i++) {
                buffer.append(genItem(list.get(i)));
            }
            FooterTemplate footerTemplate = t.getClass().getAnnotation(FooterTemplate.class);
            if (footerTemplate != null) {
                buffer.append(footerTemplate.value());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static <T> String genTable(long total, long offset, long size, List<T> list, Class<? extends T> t) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(genTable(offset, size, list, t));
        buffer.append(genPager(total, offset, size, t));
        return buffer.toString();
    }

    public static <T> String genTableWithPager(long offset, long size, List<T> list, Class<? extends T> t) {

        return genTable(list.size(), offset, size, list, t);
    }

    public static <T> String genTable(long offset, long size, List<T> list, Class<? extends T> t) {
        StringBuilder buffer = new StringBuilder();
        try {
            HeaderTemplate headerTemplate = t.getClass().getAnnotation(HeaderTemplate.class);
            if (headerTemplate != null) {
                buffer.append(headerTemplate.value());
            }
            if (list.size() > size) {
                //说明要分页了,list里面就是全部数据
                //下面需要验证一下offset+size是否大于list.size()
                if ((offset + size) > list.size()) {
                    //说明size大了，到了最后一页，那么应该以list.size()为最后一个
                    for (int i = (int) offset; i < list.size(); i++) {
                        buffer.append(genItem(list.get(i)));
                    }
                } else {
                    for (int i = (int) offset; i < (offset + size); i++) {
                        buffer.append(genItem(list.get(i)));
                    }
                }

            } else {
                for (int i = 0; i < list.size(); i++) {
                    buffer.append(genItem(list.get(i)));
                }
            }
            FooterTemplate footerTemplate = t.getClass().getAnnotation(FooterTemplate.class);
            if (footerTemplate != null) {
                buffer.append(footerTemplate.value());
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static <T> String genPager(long total, long offset, long size, Class<? extends T> t) {
        PagerTemplate pagerTemplate = t.getClass().getAnnotation(PagerTemplate.class);
        StringBuilder sb = new StringBuilder();
        if (pagerTemplate != null) {
            sb.append(pagerTemplate.begin());

            long current = (offset - (offset % size)) / size + 1;
            long totalPage = (total - (total % size)) / size + 1;

            if (totalPage > 1) {
                boolean hasNext = false;
                long show = 5;
                long startPage = 1;
                long endPage = show;
                hasNext = current < totalPage;
                if (show > totalPage) {
                    show = totalPage;
                }
                if ((current - show) < 0) {
                    startPage = 1;
                    endPage = show;

                } else if ((current + show) > totalPage) {
                    startPage = totalPage - show;
                    endPage = totalPage;

                } else {
                    show = (show - show % 2) / 2;
                    startPage = current - show;
                    endPage = current + show;

                }

                if (current <= 1) {

                    // sb.append("<li><a class=\" \" href=\"").append(toPageUrl(baseUrl, 1, size)).append("\">上一页</a></li>");
                } else {
                    sb.append(applyTextPager(offset - size, size, pagerTemplate.item(), pagerTemplate.back()));
                }

                if (current == 1) {
                    sb.append(applyCurrentPager(0, size, pagerTemplate.current()));
                } else {
                    sb.append(applyPager(0, size, pagerTemplate.item()));
                }

                if (startPage > show) {
                    sb.append(pagerTemplate.dot());

                }

                for (long i = startPage; i < endPage + 1; i++) {
                    if (i == 0 || i == 1 || i == totalPage) {
                        continue;
                    }
                    if (i == current) {
                        sb.append(applyCurrentPager(i * size - size, size, pagerTemplate.current()));

                    } else {
                        sb.append(applyPager(i * size - size, size, pagerTemplate.item()));

                    }
                }

                if (endPage + show < totalPage) {
                    sb.append(pagerTemplate.dot());
                }

                if (current == totalPage) {
                    sb.append(applyCurrentPager(totalPage * size - size, size, pagerTemplate.current()));

                } else {

                    sb.append(applyPager(totalPage * size - size, size, pagerTemplate.item()));
                }
                if (hasNext) {

                    sb.append(applyTextPager(offset + size, size, pagerTemplate.item(), pagerTemplate.next()));
                }

            }
            sb.append(pagerTemplate.end());
        }

        return sb.toString();
    }

    private static <T> String applyCurrentPager(long offset, long size, String template) {
        long page = (offset - (offset % size)) / size + 1;
        List<TemplateVariable> vars = TemplateUtil.getVariables(template);

        for (TemplateVariable var : vars) {
            String name = var.getName();
            if (name.equals("page")) {
                var.setValue(String.valueOf(page));
            } else if (name.equals("offset")) {
                var.setValue(String.valueOf(offset));
            } else if (name.equals("size")) {
                var.setValue(String.valueOf(size));
            }
        }
        return TemplateUtil.getResult(vars, template);
    }

    private static <T> String applyPager(long offset, long size, String template) {
        long page = (offset - (offset % size)) / size + 1;
        List<TemplateVariable> vars = TemplateUtil.getVariables(template);

        for (TemplateVariable var : vars) {
            String name = var.getName();
            if (name.equals("page")) {
                var.setValue(String.valueOf(page));
            } else if (name.equals("offset")) {
                var.setValue(String.valueOf(offset));
            } else if (name.equals("size")) {
                var.setValue(String.valueOf(size));
            }
        }
        return TemplateUtil.getResult(vars, template);
    }

    private static <T> String applyTextPager(long offset, long size, String template, String text) {
        List<TemplateVariable> vars = TemplateUtil.getVariables(template);
        for (TemplateVariable var : vars) {
            String name = var.getName();
            if (name.equals("page")) {
                var.setValue(text);
            } else if (name.equals("offset")) {
                var.setValue(String.valueOf(offset));
            } else if (name.equals("size")) {
                var.setValue(String.valueOf(size));
            }
        }
        return TemplateUtil.getResult(vars, template);
    }

    private static <T> String genItem(T t) {
        try {
            ItemTemplate itemTemplate = t.getClass().getAnnotation(ItemTemplate.class);
            if (itemTemplate != null) {
                return applyTemplate(t, itemTemplate.value());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    private static <T> String applyTemplate(T t, String template) {
        List<TemplateVariable> vars = TemplateUtil.getVariables(template);
        Map<String, String> map = genItemFieldMap(t);
        for (TemplateVariable var : vars) {
            String name = var.getName();
            if (map.containsKey(name)) {
                var.setValue(map.get(name));
            }
        }
        return TemplateUtil.getResult(vars, template);
    }

    public static <T> Map<String, String> genItemFieldMap(T t) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            Field[] fs = t.getClass().getDeclaredFields();
            for (Field f : fs) {
                String data = get(t, f);
                if (data == null) {
                    continue;
                }
                ItemField itemField = f.getAnnotation(ItemField.class);
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
            ItemField itemField = f.getAnnotation(ItemField.class);
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
