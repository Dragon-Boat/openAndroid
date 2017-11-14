package com.sundae.zl.openandroid;

import android.app.Application;
import android.content.Context;

/**
 * Created by @author hzzhoulong
 * on 2017-10-23.
 * # Copyright 2017 netease. All rights reserved.
 */

public class OpenAndroidApplication extends Application {
	private static OpenAndroidApplication instance;

	final Context context = this;

	public static OpenAndroidApplication getInstance() {
		if (instance == null) {
			throw new RuntimeException("OpenAndroidApplication not created");
		}
		return instance;
	}

	public static Context getAppContext() {
		return getInstance().getApplicationContext();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}
}
