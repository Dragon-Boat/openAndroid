package com.sundae.zl.openandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sundae.zl.openandroid.R;
import com.sundae.zl.openandroid.utils.DataSource;

import java.util.List;
/**
 * Created by @author hzzhoulong
 * on 2017-8-14.
 * # Copyright 2017 netease. All rights reserved.
 */

public class SwapAdapterDemoFragment extends BaseUtilFragment {

	public static SwapAdapterDemoFragment instance() {
		return new SwapAdapterDemoFragment();
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
			return new BaseViewHolder(new TextView(parent.getContext()));
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

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.swapadapter_demo, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		final RecyclerView recyclerView = $(view, R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

		final BaseAdapter left = new BaseAdapter(DataSource.simpleGet("left"));
		final BaseAdapter right = new BaseAdapter(DataSource.simpleGet("right"));

		$(view, R.id.left_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				recyclerView.swapAdapter(left, false);
			}
		});
		$(view, R.id.right_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				recyclerView.swapAdapter(right, false);
			}
		});

		recyclerView.setAdapter(left);

	}

}
