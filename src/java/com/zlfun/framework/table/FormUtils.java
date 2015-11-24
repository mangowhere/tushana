/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.table;

import com.zlfun.framework.MiscDateUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hubin
 */
public class FormUtils {

    public static <T> String genForm(T t) {
        Map<String, String> map = genFormFieldMap(t);
        StringBuilder buffer = new StringBuilder();
        try {
            Form form = t.getClass().getAnnotation(Form.class);
            if (form != null) {
                buffer.append(form.header());
            }
            Field[] fs = t.getClass().getDeclaredFields();
            for (Field f : fs) {

                FormField formField = f.getAnnotation(FormField.class);
                if (formField == null) {
                    continue;
                }
                buffer.append(genField(f, map));
                // 处理父类的
                Class<?> parent = t.getClass().getSuperclass();
                while (parent != Object.class) {
                    parent(buffer, parent, map);
                    parent = parent.getSuperclass();
                }

            }
            if (form != null) {
                buffer.append(form.footer());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buffer.toString();

    }

    private static <T> String genOption(List<T> list, Option option) {

        StringBuilder buffer = new StringBuilder();
        for (T t : list) {
            buffer.append(applyTemplate(TableUtils.genItemFieldMap(t), option.value()));
        }
        return buffer.toString();
    }

    private static <T> String applyTemplate(Map<String, String> map, String template) {

        List<TemplateVariable> vars = TemplateUtil.getVariables(template);

        for (TemplateVariable var : vars) {
            String name = var.getName();

            if (map.containsKey(name)) {
                var.setValue(map.get(name));
            }

        }

        return TemplateUtil.getResult(vars, template);
    }

    private static void parent(StringBuilder buffer, Class<?> parentClass, Map<String, String> map) {
        Field[] fs = parentClass.getDeclaredFields();

        for (Field f : fs) {

            FormField formField = f.getAnnotation(FormField.class);
            if (formField == null) {
                continue;
            }

            buffer.append(genField(f, map));

        }

    }

    private static <T> String genField(Field fs, Map<String, String> map) {
        try {
            FormField formField = fs.getAnnotation(FormField.class);
            if (formField != null) {

                return applyTemplate(map, formField);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    private static <T> String applyTemplate(Map<String, String> map, FormField formField) {

        List<TemplateVariable> vars = TemplateUtil.getVariables(formField.value());

        for (TemplateVariable var : vars) {
            String name = var.getName();
            if (map.containsKey(name)) {
                var.setValue(map.get(name));
            }

        }
        return TemplateUtil.getResult(vars, formField.value());
    }

    private static <T> Map<String, String> genFormFieldMap(T t) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            Field[] fs = t.getClass().getDeclaredFields();
            for (Field f : fs) {

                Option option = f.getAnnotation(Option.class);
                if (option != null) {
                    String fname = f.getName();
                    if (!map.containsKey(fname)) {
                        map.put(fname, genOption(getList(t, f), option));
                    }
                }

                String data = get(t, f);
                if (data == null) {
                    continue;

                }
                FormField formField = f.getAnnotation(FormField.class);
                if (formField == null) {
                    continue;
                }

                String fname = f.getName();
                if (!map.containsKey(fname)) {
                    map.put(fname, data);
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
            FormField formField = f.getAnnotation(FormField.class
            );
            if (formField
                    == null) {
                continue;
            }

            String fname = f.getName();
            if (!map.containsKey(fname)) {
                map.put(fname, data);
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
                if (field.get(obj)
                        == null) {
                    return "";
                }

                return String.valueOf(field.get(obj));
            } else if (field.getType() == Date.class) {
                if (field.get(obj)
                        == null) {
                    return "";
                }

                return MiscDateUtils.getDateTime(
                        (Date) field.get(obj));
            }

        } catch (Exception e) {

        }
        return "";
    }

    private static <T> List<T> getList(Object t, Field field) {

        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);

            }

            if (field.getType() == List.class) {
                return (List<T>) field.get(t);
            }

        } catch (Exception e) {

        }
        return new ArrayList();
    }
}
