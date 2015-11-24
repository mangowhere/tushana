package com.zlfun.framework.result;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResultParser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String json = "{\t\"retcode\":10004,\"test\":{\"bad\":\"good\",test1:{abd:bad,cde:ad},\"goodmorning\":\"hello\"},\"errormsg\":\"token invalid\"}";
		fromResult(json);
	}

	public static Map<String, String> fromResult(String json) {

		int i = 0;
		Map<String, String> map = new HashMap<String, String>();

		try {
			ResultRuntime runtime = new ResultRuntime();
			CharArrayReader cr = new CharArrayReader(json.toCharArray());

			char ch = ' ';

			runtime.step = ResultStep.BEGIN;

			while ((i = cr.read()) != -1) {

				ch = (char) i;
				switch (runtime.step) {
				case BEGIN:
					ResultStepUtils.begin(runtime, ch);
					break;
				case END:
					ResultStepUtils.end(runtime, ch);
					break;
				case KEY:
					ResultStepUtils.key(runtime, ch);
					break;
				case KEY_CLOSE:
					ResultStepUtils.keyClose(runtime, ch);
					break;
				case KEY_END:
					ResultStepUtils.keyEnd(runtime, ch);
					break;
				case KEY_IGNORE:
					ResultStepUtils.keyIgnore(runtime, ch);
					break;
				case VALUE:
					ResultStepUtils.value(runtime, ch);
					break;
				case VALUE_CLOSE:
					ResultStepUtils.valueClose(runtime, ch);
					break;
				case VALUE_END:
					ResultStepUtils.valueEnd(runtime, ch);
					break;
				case VALUE_IGNORE:
					ResultStepUtils.valueIgnore(runtime, ch);
					break;
				default:
					break;

				}

			}
			runtime.print();
			map = runtime.map;
		} catch (IOException ex) {

		}

		return map;
	}

}
