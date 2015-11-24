package com.zlfun.framework;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Action<T> {

	private boolean io = false;;

	public boolean isIo() {
		return io;
	}

	public void setIo(boolean io) {
		this.io = io;
	}
        private static Gson json=new Gson();

	protected abstract T proceed(HttpServletRequest request, HttpServletResponse response)
			throws FrameworkException;

	public String proc(HttpServletRequest request, HttpServletResponse response) {
		String page = WebEntityUtils.getNameRestFul(request);

		try {
			T t = proceed(request, response);

			LogUtils.log("[response]" + page, t);
                        
			String result = json.toJson(t);
                        //String result = ResultUtils.toResult(t);
			return result;
		} catch (FrameworkException e) {
			// TODO Auto-generated catch block

			LogUtils.error(e.getSource(), "[error]" + page, e.getResult());

			String result = ResultUtils.toResult(e.getResult());

			return result;

		} finally {

		}

	}

}
