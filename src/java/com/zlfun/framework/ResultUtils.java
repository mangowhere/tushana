package com.zlfun.framework;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.zlfun.framework.result.ResultParser;

public class ResultUtils

{

    private static <T> T fill(Map<String, String> map, T t)
    {
        T bean = t;
        try
        {

            if (t.getClass().getAnnotation(WebEntity.class) == null)
            {

                Field[] fs = t.getClass().getDeclaredFields();
                for (Field f : fs)
                {

                    set(bean, f, map.get(f.getName()));

                }
            }
            else
            {
                Field[] fs = t.getClass().getDeclaredFields();
                for (Field f : fs)
                {
                    WebParam param = f.getAnnotation(WebParam.class);
                    if (param == null)
                        continue;
                    String fname;
                    if ("".equals(param.value()))
                    {
                        fname = f.getName();
                    }
                    else
                    {
                        fname = param.value();
                    }

                    set(bean, f, map.get(fname));

                }

            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bean;
    }

    public static <T> T fill(String result, T t)
    {
        if (result == null)
            return null;
        return fill(ResultParser.fromResult(result), t);
    }

    private static void set(Object obj, Field field, String value)
    {

        if (value == null)
            return;
        try
        {
            if (!field.isAccessible())
            {
                field.setAccessible(true);
            }
            Object val = value;

            if (field.getType() == int.class)
            {
                try
                {
                    val = Integer.parseInt(value);
                }
                catch (Exception ex)
                {
                    val = -1;
                }
            }
            else if (field.getType() == long.class)
            {
                try
                {
                    val = Long.parseLong(value);
                }
                catch (Exception ex)
                {
                    val = -1l;
                }

            }
            else if (field.getType() == double.class)
            {
                val = Double.parseDouble(value);
            }
            else if (field.getType() == byte.class)
            {
                val = Byte.parseByte(value);
            }
            else if (field.getType() == boolean.class)
            {
                val = Boolean.parseBoolean(value);
            }

            field.set(obj, val);

        }
        catch (Exception e)
        {

        }
    }

    private static boolean parent(Object o, Class<?> parentClass, StringBuilder buffer, boolean comma)
    {
        Field[] fs = parentClass.getDeclaredFields();

        for (Field f : fs)
        {
            String data = get(o, f);
            if (data == null)
            {
                continue;
            }
            WebParam param = f.getAnnotation(WebParam.class);
            if (param == null)
                continue;
            if (comma)
            {
                buffer.append(",");
            }
            comma = true;

            String fname;
            if ("".equals(param.value()))
            {
                fname = f.getName();
            }
            else
            {
                fname = param.value();
            }
            buffer.append("\"");
            buffer.append(fname);
            buffer.append("\":");
            buffer.append(data);

        }
        return comma;
    }

    public static <T> String toResult(List<T> list)
    {
        if (list == null || list.size() == 0)
            return null;
        StringBuilder buffer = new StringBuilder();
        for (T t : list)
        {
            buffer.append(toResult(t));
        }
        return buffer.toString();
    }

    public static String toResult(Object o)
    {
        StringBuilder buffer = new StringBuilder();

        try
        {

            if (o.getClass().getAnnotation(WebEntity.class) == null)
            {

                return "";
            }
            else
            {
                Field[] fs = o.getClass().getDeclaredFields();
                buffer.append("{");
                boolean comma = false;
                for (Field f : fs)
                {
                    String data = get(o, f);
                    if (data == null)
                    {
                        continue;
                    }
                    WebParam param = f.getAnnotation(WebParam.class);
                    if (param == null)
                        continue;
                    if (comma)
                    {
                        buffer.append(",");
                    }
                    comma = true;

                    String fname;
                    if ("".equals(param.value()))
                    {
                        fname = f.getName();
                    }
                    else
                    {
                        fname = param.value();
                    }
                    buffer.append("\"");
                    buffer.append(fname);
                    buffer.append("\":");
                    buffer.append(data);

                }
                // 处理父类的
                Class<?> parent = o.getClass().getSuperclass();
                while (parent != Object.class)
                {

                    if (parent.getAnnotation(WebEntity.class) != null)
                    {
                        comma = parent(o, parent, buffer, comma);
                        parent = parent.getSuperclass();
                    }
                    else
                    {
                        break;
                    }

                }
                buffer.append("}");

            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public static <T> String toUrlParam(T o)
    {
        StringBuilder buffer = new StringBuilder();

        try
        {

            if (o.getClass().getAnnotation(WebEntity.class) == null)
            {
                Field[] fs = o.getClass().getDeclaredFields();
                buffer.append("?");
                boolean comma = false;
                for (Field f : fs)
                {
                    if (comma)
                    {
                        buffer.append("&");
                    }
                    comma = true;

                    buffer.append(f.getName());
                    buffer.append("=");
                    buffer.append(getParam(o, f));

                }

            }
            else
            {
                Field[] fs = o.getClass().getDeclaredFields();
                buffer.append("{");
                boolean comma = false;
                for (Field f : fs)
                {
                    if (comma)
                    {
                        buffer.append(",");
                    }
                    comma = true;
                    WebParam param = f.getAnnotation(WebParam.class);
                    if (param == null)
                    {
                        continue;
                    }
                    String fname;
                    if ("".equals(param.value()))
                    {
                        fname = f.getName();
                    }
                    else
                    {
                        fname = param.value();
                    }
                    buffer.append("\"");
                    buffer.append(fname);
                    buffer.append("\":");
                    buffer.append(getParam(o, f));

                }
                buffer.append("}");

            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return buffer.toString();
    }

    private static String getParam(Object obj, Field field)
    {

        try
        {
            if (!field.isAccessible())
            {
                field.setAccessible(true);
            }

            if (field.getType() == int.class)
            {
                return String.valueOf(field.getInt(obj));

            }
            else if (field.getType() == long.class)
            {
                return String.valueOf(field.getLong(obj));

            }
            else if (field.getType() == double.class)
            {
                return String.valueOf(field.getDouble(obj));
            }
            else if (field.getType() == byte.class)
            {
                return String.valueOf(field.getByte(obj));
            }
            else if (field.getType() == boolean.class)
            {
                return String.valueOf(field.getBoolean(obj));
            }
            else if (field.getType() == String.class)
            {
                if (field.get(obj) == null)
                    return null;
                return URLEncoder.encode(String.valueOf(field.get(obj)), "UTF-8");
            }

        }
        catch (Exception e)
        {

        }
        return "";
    }

    private static String get(Object obj, Field field)
    {

        try
        {
            if (!field.isAccessible())
            {
                field.setAccessible(true);
            }

            if (field.getType() == int.class)
            {
                return String.valueOf(field.getInt(obj));

            }
            else if (field.getType() == long.class)
            {
                return String.valueOf(field.getLong(obj));

            }
            else if (field.getType() == double.class)
            {
                return String.valueOf(field.getDouble(obj));
            }
            else if (field.getType() == byte.class)
            {
                return String.valueOf(field.getByte(obj));
            }
            else if (field.getType() == boolean.class)
            {
                return String.valueOf(field.getBoolean(obj));
            }
            else if (field.getType() == String.class)
            {
                if (field.get(obj) == null)
                    return null;
                return "\"" + String.valueOf(field.get(obj)) + "\"";
            }

        }
        catch (Exception e)
        {

        }
        return "";
    }
}
