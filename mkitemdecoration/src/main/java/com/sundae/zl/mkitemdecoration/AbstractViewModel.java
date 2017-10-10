package com.sundae.zl.mkitemdecoration;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;
/**
 * Created by @author hzzhoulong
 * on 2017-10-10.
 * # Copyright 2017 netease. All rights reserved.
 */

public abstract class AbstractViewModel<T> {

	List<T> data;
	View view;

	public AbstractViewModel(List<T> data, View view) {
		this.data = data;
		this.view = view;
	}

	public AbstractViewModel(Context context, List<T> data, @LayoutRes int resId) {
		this.data = data;
		this.view = LayoutInflater.from(context).inflate(resId, null, false);
	}

	public abstract void bindView(View view, int position);
}
