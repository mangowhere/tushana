package com.zlfun.framework;

public class FrameworkException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8276170831656932018L;

	private WebResult result;

	private String source;

	public WebResult getResult() {
		return result;
	}

	public FrameworkException(WebResult result) {
		this.result = result;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource()

	{
		if (source == null) {
			source = "";
		}
		return source;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

}
