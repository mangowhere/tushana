package com.zlfun.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class EntityAction<IN, OUT> extends Action<OUT> {

	@Override
	public OUT proceed(HttpServletRequest request, HttpServletResponse response)
			throws FrameworkException {
		// TODO Auto-generated method stub
		IN in = WebEntityUtils.fill(request, createEntity());
		String page = WebEntityUtils.getNameRestFul(request);
	 
		LogUtils.log("[api]"+page,  in);
		return proceed(in);
	}

	public abstract OUT proceed(IN in) throws FrameworkException;

	public abstract IN createEntity();

}
