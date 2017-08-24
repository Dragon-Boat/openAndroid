package com.sundae.zl.openandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.just.library.AgentWeb;
import com.sundae.zl.openandroid.R;
/**
 * Created by @author hzzhoulong
 * on 2017-8-24.
 * # Copyright 2017 netease. All rights reserved.
 */

public class AgentWebFragment extends BaseUtilFragment {

	AgentWeb agentWeb;
	public static Fragment instance(){
		return new AgentWebFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.agent_web_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}
}
