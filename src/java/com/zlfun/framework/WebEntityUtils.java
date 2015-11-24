package com.zlfun.framework;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

public class WebEntityUtils {

	public static <T> T fill(HttpServletRequest request, Class<T> cls) {
		T bean = null;
		try {
			bean = cls.newInstance();
			Field[] fs = cls.getDeclaredFields();
			for (Field f : fs) {

				set(bean, f, getString(request, f.getName()));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}

	public static <T> T fill(HttpServletRequest request, T t) {
		T bean = t;
		try {

			if (t.getClass().getAnnotation(WebEntity.class) == null) {

				Field[] fs = t.getClass().getDeclaredFields();
				for (Field f : fs) {

					set(bean, f, getString(request, f.getName()));

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

						set(bean, f, getString(request, fname));
					}

				}

				// 处理父类的
				Class<?> parent = t.getClass().getSuperclass();
				while (parent != Object.class) {

					if (parent.getAnnotation(WebEntity.class) != null) {

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

								set(bean, f, getString(request, fname));
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

		if (value == null)
			return;
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

	public static String getString(HttpServletRequest request, String key) {
		String ret = "";
		if (request.getParameter(key) != null) {
			ret = (String) request.getParameter(key);
		} else {
			if (request.getAttribute(key) != null) {
				ret = (String) request.getAttribute(key);
			}
		}
		return ret;
	}

	public static long getLong(HttpServletRequest request, String key) {
		long ret = -1;
		if (request.getParameter(key) != null) {
			ret = Long.parseLong(request.getParameter(key));
		} else {
			if (request.getAttribute(key) != null) {
				ret = Long.parseLong((String) request.getAttribute(key));
			}
		}
		return ret;
	}

	public static int getInt(HttpServletRequest request, String key) {
		int ret = -1;
		if (request.getParameter(key) != null) {
			ret = Integer.parseInt(request.getParameter(key));
		} else {
			if (request.getAttribute(key) != null) {
				ret = Integer.parseInt((String) request.getAttribute(key));
			}
		}
		return ret;
	}

	public static String getName(HttpServletRequest request) {
		String uri = request.getRequestURI();
		int end = uri.lastIndexOf(".");
		if (end == -1) {
			end = uri.length();
		}
		int start = uri.lastIndexOf("/");
		if (start == -1) {
			return null;
		} else {
			if (start == uri.length()) {
				return uri.substring(0, uri.length() - 1);
			}

		}
		return uri.substring(start + 1, end);

	}

	public static String getNameRestFul(HttpServletRequest request) {
		String uri = request.getRequestURI();

		int begin = uri.indexOf("/");
		String result = uri.substring(begin + 1, uri.length());
		begin = result.indexOf("/");
		result = result.substring(begin + 1, result.length());
		begin = result.indexOf("/");
		result = result.substring(begin + 1, result.length());
		 
		int end = result.indexOf("/");
		if (end == -1) {
			end = result.length();
		}
		return result.substring(0,  end);
	}
	
	public static String getPath(HttpServletRequest request) {
		String uri = request.getRequestURI();

		int begin = uri.indexOf("/");
		String result = uri.substring(begin + 1, uri.length());
		begin = result.indexOf("/");
		result = result.substring(begin + 1, result.length());
		begin = result.indexOf("/");
		result = result.substring(begin + 1, result.length());
		begin = result.indexOf("/");
		if (begin == -1) {
			return null;
		} else {
			if (begin == result.length()) {
				return uri.substring(0, result.length() - 1);
			}

		}
		int end = result.lastIndexOf(".");
		if (end == -1) {
			end = result.length();
		}
		return result.substring(begin + 1, end);
	}
}
