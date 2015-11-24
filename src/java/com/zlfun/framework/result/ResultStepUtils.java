package com.zlfun.framework.result;

public class ResultStepUtils {

	private final static char[] none = { '\'', '\"', '{', '}', '[', ']', ',', ':' };

	private final static char[] ignore = { '\t', '\n', ' ' };

	private final static char[] begin_key_ignore = { '\"', '\'' };

	public static boolean begin(ResultRuntime runtime, char ch) {
		if (in(ignore, ch)) {
			runtime.step = ResultStep.BEGIN;
			return true;
		} else if (in(begin_key_ignore, ch)) {
			runtime.step = ResultStep.KEY_IGNORE;
			return true;

		} else if (!in(none, ch)) {
			runtime.step = ResultStep.KEY;
			runtime.append(ch);
			return true;
		} else {
			return false;
		}

	}

	public static boolean end(ResultRuntime runtime, char ch) {

		return true;
	}

	private final static char[] key_ignore_key_close = { '\"', '\'' };
	private final static char[] key_ignore_key_end = { ':' };

	public static boolean keyIgnore(ResultRuntime runtime, char ch) {
		if (!in(none, ch)) {
			runtime.step = ResultStep.KEY;
			runtime.append(ch);

			return true;

		} else if (in(key_ignore_key_close, ch)) {
			runtime.step = ResultStep.KEY_CLOSE;
			runtime.key();
			return true;
		} else if (in(key_ignore_key_end, ch)) {
			runtime.step = ResultStep.KEY_END;
			runtime.key();
			return true;
		} else {
			return false;
		}
	}

	private final static char[] key_key_end = { ':' };
	private final static char[] key_key_close = { '\"', '\'' };

	public static boolean key(ResultRuntime runtime, char ch) {
		if (!in(none, ch)) {
			runtime.step = ResultStep.KEY;
			runtime.append(ch);

			return true;

		} else if (in(key_key_close, ch)) {
			runtime.step = ResultStep.KEY_CLOSE;
			runtime.key();
			return true;
		} else if (in(key_key_end, ch)) {
			runtime.step = ResultStep.KEY_END;
			runtime.key();
			return true;
		} else {
			return false;
		}
	}

	private final static char[] key_close_key_end = { ':' };

	public static boolean keyClose(ResultRuntime runtime, char ch) {
		if (in(ignore, ch)) {
			runtime.step = ResultStep.KEY_CLOSE;

			return true;

		} else if (in(key_close_key_end, ch)) {
			runtime.step = ResultStep.KEY_END;
			return true;
		} else {
			return false;
		}
	}

	private final static char[] key_end_value_ingore = { '\"', '\'' };
	private final static char[] key_end_child_begin = { '{' };

	public static boolean keyEnd(ResultRuntime runtime, char ch) {
		if (in(ignore, ch)) {
			runtime.step = ResultStep.KEY_END;

			return true;

		} else if (!in(none, ch)) {
			runtime.step = ResultStep.VALUE;
			runtime.append(ch);
			return true;
		} else if (in(key_end_value_ingore, ch)) {
			runtime.step = ResultStep.VALUE_IGNORE;

			return true;
		} else if (in(key_end_child_begin, ch)) {
			runtime.step = ResultStep.BEGIN;
			// 这里需要压栈
			runtime.push(ResultStep.VALUE_CLOSE);

			return true;
		} else {
			return false;
		}
	}

	private final static char[] value_ignore_value_end = { ',' };
	private final static char[] value_ignore_value_close = { '\"', '\'' };
	private final static char[] noneValue = { '\'', '\"', '{', '}', '[', ']', ',' };
	public static boolean valueIgnore(ResultRuntime runtime, char ch) {
		if (!in(noneValue, ch)) {
			runtime.step = ResultStep.VALUE;
			runtime.append(ch);
			return true;
		} else if (in(value_ignore_value_end, ch)) {
			runtime.step = ResultStep.VALUE_END;
			runtime.value();

			return true;
		} else if (in(value_ignore_value_close, ch)) {
			runtime.step = ResultStep.VALUE_CLOSE;
			runtime.value();

			return true;
		} else {
			return false;
		}
	}

	private final static char[] value_value_end = { ',' };
	private final static char[] value_value_close = { '\"', '\'' };

	private final static char[] value_x_end = { '}' };

	public static boolean value(ResultRuntime runtime, char ch) {
		if (!in(noneValue, ch)) {
			runtime.step = ResultStep.VALUE;
			runtime.append(ch);
			return true;
		} else if (in(value_value_end, ch)) {
			runtime.step = ResultStep.VALUE_END;
			runtime.value();

			return true;
		} else if (in(value_value_close, ch)) {
			runtime.step = ResultStep.VALUE_CLOSE;
			runtime.value();

			return true;
		} else if (in(value_x_end, ch)) {
			runtime.value();
			if (runtime.needPop()) {
			
				runtime.pop();

			} else {
				runtime.step = ResultStep.END;
			 
			}

			return true;
		} else {
			return false;
		}
	}

	private final static char[] value_close_value_end = { ',' };

	public static boolean valueClose(ResultRuntime runtime, char ch) {
		if (in(ignore, ch)) {
			runtime.step = ResultStep.VALUE_CLOSE;

			return true;

		} else if (in(value_close_value_end, ch)) {
			runtime.step = ResultStep.VALUE_END;
			return true;
		} else if (in(value_x_end, ch)) {
			runtime.value();
			if (runtime.needPop()) {

				runtime.pop();

			} else {
				runtime.step = ResultStep.END;

			}

			return true;
		} else {
			return false;
		}
	}

	private final static char[] value_end_key_ignore = { '\"', '\'' };

	public static boolean valueEnd(ResultRuntime runtime, char ch) {
		if (in(ignore, ch)) {
			runtime.step = ResultStep.VALUE_END;

			return true;

		} else if (!in(none, ch)) {
			runtime.step = ResultStep.KEY;
			runtime.append(ch);
			return true;
		} else if (in(value_end_key_ignore, ch)) {
			runtime.step = ResultStep.KEY_IGNORE;

			return true;
		} else {
			return false;
		}
	}

	private static boolean in(char[] table, char ch) {
		for (char tch : table) {
			if (tch == ch) {
				return true;
			}
		}
		return false;
	}
}
