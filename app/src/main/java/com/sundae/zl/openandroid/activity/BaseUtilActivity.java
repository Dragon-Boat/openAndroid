package com.sundae.zl.openandroid.activity;

import android.support.annotation.IdRes;
import android.view.View;
/**
 * Created by @author hzzhoulong
 * on 2017-8-14.
 * # Copyright 2017 netease. All rights reserved.
 */

public class BaseUtilActivity extends BaseActivity {

	@SuppressWarnings("unchecked")
	public <T extends View> T $(@IdRes int resId) {
		return (T) findViewById(resId);
	}
}
