package com.sundae.zl.openandroid.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sundae.zl.openandroid.R;
import com.sundae.zl.openandroid.databinding.DataBindingDemoBinding;
import com.sundae.zl.openandroid.utils.DataSource;

import java.util.List;
/**
 * Created by @author hzzhoulong
 * on 2017-8-21.
 * # Copyright 2017 netease. All rights reserved.
 */

public class DataBindingFragment extends BaseUtilFragment implements SwipeRefreshLayout.OnRefreshListener{
	public static Fragment instance() {
		return new DataBindingFragment();
	}
	DataBindingDemoBinding binding;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.data_binding_demo, container, false);
		return binding.getRoot();
	}

	BaseAdapter adapter;
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		binding.swipeRefreshLayout.setOnRefreshListener(this);
		binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
				LinearLayoutManager.VERTICAL, false));
		adapter = new BaseAdapter(DataSource.simpleGet("dataBinding"));
		binding.recyclerView.setAdapter(adapter);
		binding.swipeRefreshLayout.setColorSchemeColors(
				0xff3e802f,
				0xfff4b400,
				0xff427fed,
				0xffb23424);
		binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

	}

	@Override
	public void onRefresh() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				binding.swipeRefreshLayout.setRefreshing(false);
				adapter.data = DataSource.simpleGet("refresh" + (int)  (1 + Math.random() * 10) + ": ",
						(int) (Math.random() * 100));
				adapter.notifyDataSetChanged();
			}
		}, 6000);

	}

	private class BaseAdapter extends RecyclerView.Adapter {
		List<String> data;

		public BaseAdapter(List<String> data) {
			super();
			this.data = data;
		}

		class BaseViewHolder extends RecyclerView.ViewHolder {
			TextView textView;

			public BaseViewHolder(View itemView) {
				super(itemView);
				textView = ((TextView) itemView);
			}

			public void render(String name) {
				textView.setText(name);

			}
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			TextView textView = new TextView(parent.getContext());
			textView.setGravity(Gravity.CENTER);
			textView.setPadding(15, 15, 15, 15);
			return new BaseViewHolder(textView);
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
			((BaseViewHolder) holder).render(data.get(position));
		}

		@Override
		public int getItemCount() {
			return data.size();
		}
	}
}
