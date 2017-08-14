package com.sundae.zl.openandroid.utils;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by @author hzzhoulong
 * on 2017-8-14.
 * # Copyright 2017 netease. All rights reserved.
 */

public class DataSource {
	public static <T> List<String> simpleGet(T t,int size) {
		List<String> result = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			result.add(t.toString() + i);
		}
		return result;
	}

	public static <T> List<String> simpleGet(T t) {
		return simpleGet(t, (int) (Math.random() * 100 + 10));
	}
}
