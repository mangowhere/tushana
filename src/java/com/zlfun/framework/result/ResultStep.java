package com.zlfun.framework.result;

public enum ResultStep {
 

	BEGIN,

	END,

	KEY_IGNORE, // 当前不存入

	KEY, // 当前存入

	KEY_CLOSE,

	KEY_END,

	VALUE_IGNORE,

	VALUE,

	VALUE_CLOSE,

	VALUE_END;

}
