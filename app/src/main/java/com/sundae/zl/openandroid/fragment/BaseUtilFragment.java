package com.sundae.zl.openandroid.fragment;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
/**
 * Created by @author hzzhoulong
 * on 2017-8-14.
 * # Copyright 2017 netease. All rights reserved.
 */

public class BaseUtilFragment extends BaseFragment {
	@SuppressWarnings("unchecked")
	public <T extends View> T $(@NonNull View container, @IdRes int resId) {
		return (T) container.findViewById(resId);
	}
}
