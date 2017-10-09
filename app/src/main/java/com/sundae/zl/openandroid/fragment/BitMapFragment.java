package com.sundae.zl.openandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sundae.zl.openandroid.R;
/**
 * Created by @author hzzhoulong
 * on 2017-9-30.
 * # Copyright 2017 netease. All rights reserved.
 */

public class BitMapFragment extends BaseUtilFragment{
	public static Fragment instance() {
		return new BitMapFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return LayoutInflater.from(getContext()).inflate(R.layout.bitmap_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}
}
