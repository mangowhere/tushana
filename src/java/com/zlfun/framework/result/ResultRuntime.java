package com.zlfun.framework.result;

import java.io.CharArrayWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

public class ResultRuntime {
	public String value;
	public String key;
	public List<String> list;
	public Map<String, String> map = new HashMap<String, String>();

	public ResultStep step;
	private Stack<ResultStep> stack = new Stack<ResultStep>();
	private Stack<String> keyStack = new Stack<String>();

	private CharArrayWriter buffer = new CharArrayWriter();

	public void key() {

		key = buffer.toString();
		buffer.reset();
	}

	private String stackKey() {
		StringBuilder buffer = new StringBuilder();
		Iterator<String> it = keyStack.iterator();
		while (it.hasNext()) {
			buffer.append(it.next());
			buffer.append('.');
		}
		return buffer.toString();
	}

	public void value() {
		value = buffer.toString();
		buffer.reset();
		if (key != null && value != null) {
			map.put(stackKey() + key, value);
			key = null;
			value = null;
		}

	}

	public void print() {
		for (Entry<String, String> s : map.entrySet()) {
			System.out.println(s.getKey() + "  " + s.getValue());
		}
	}

	public void item() {
		String item = buffer.toString();
		list.add(item);
		buffer.reset();
	}

	public boolean needPop() {
		return stack.size() > 0;
	}

	public void append(char ch) {
		buffer.append(ch);
	}

	public void pop() {
		key = keyStack.pop();
		step = stack.pop();
	}

	public void push(ResultStep step) {
		stack.push(step);
		keyStack.push(key);
	}

}
