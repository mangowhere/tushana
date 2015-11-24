package com.zlfun.framework;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class LogUtils {
	private static Logger logger = Logger.getLogger(LogUtils.class.getName());

	private static boolean parent(Object o, Class<?> parentClass, StringBuilder buffer,
			boolean comma) {
		Field[] fs = parentClass.getDeclaredFields();

		for (Field f : fs) {
			String data = get(o, f);
			if (data == null) {
				continue;
			}
			WebParam param = f.getAnnotation(WebParam.class);
			if (param == null)
				continue;
			if (comma) {
				buffer.append(",");
			}
			comma = true;

			String fname;
			if ("".equals(param.value())) {
				fname = f.getName();
			} else {
				fname = param.value();
			}
			buffer.append("\"");
			buffer.append(fname);
			buffer.append("\"=");
			buffer.append(data);

		}
		return comma;
	}

	public static void log(String source, String api, Object o) {
		logger.info("FROM:" + source + "," + toLog(api, o));
	}

	public static void error(String source, String api, Object o) {
		logger.severe("FROM:" + source + "," + toLog(api, o));
	}

	public static void log(String api, Object o) {
		logger.info(toLog(api, o));
	}

	private static boolean needLog(String name) {
		if (name.equals("passwd")) {
			return false;

		}
		return true;
	}

	private static String toLog(String api, Object o) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(api);

		try {

			if (o.getClass().getAnnotation(WebEntity.class) != null) {

				Field[] fs = o.getClass().getDeclaredFields();
				buffer.append(":");
				boolean comma = false;
				for (Field f : fs) {
					String data = get(o, f);
					if (data == null) {
						continue;
					}
					WebParam param = f.getAnnotation(WebParam.class);
					if (param == null)
						continue;
					String fname;
					if ("".equals(param.value())) {
						fname = f.getName();
					} else {
						fname = param.value();
					}
					if (!needLog(fname)) {
						continue;
					}
					if (comma) {
						buffer.append(",");
					}
					comma = true;

					buffer.append("\"");
					buffer.append(fname);
					buffer.append("\"=");
					buffer.append(data);

				}
				// 处理父类的
				Class<?> parent = o.getClass().getSuperclass();
				while (parent != Object.class) {

					if (parent.getAnnotation(WebEntity.class) != null) {
						comma = parent(o, parent, buffer, comma);
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

		return buffer.toString();
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
				if (field.get(obj) == null)
					return null;
				return "\"" + String.valueOf(field.get(obj)) + "\"";
			}

		} catch (Exception e) {

		}
		return "";
	}
}
