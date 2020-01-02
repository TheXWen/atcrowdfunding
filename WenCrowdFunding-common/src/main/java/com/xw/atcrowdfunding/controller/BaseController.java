package com.xw.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
	// private Map<String, Object> resultMap; //不能使用成员变量，因为控制器对象是单例的。会出现多线程并发问题
	// 通过ThreadLocal来共享数据
	private ThreadLocal<Map<String, Object>> datas = new ThreadLocal<Map<String, Object>>();

	protected void start() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		datas.set(resultMap);
	}

	public Object end() {
		Map<String, Object> resultMap = datas.get();
		datas.remove();
		return resultMap;
	}

	public void success(boolean flg) {
		Map<String, Object> resultMap = datas.get();
		resultMap.put("success", flg);
	}

	public void param(String key, Object val) {
		Map<String, Object> resultMap = datas.get();
		resultMap.put(key, val);
	}

	public void error(String msg) {
		Map<String, Object> resultMap = datas.get();
		resultMap.put("message", msg);
	}

}
