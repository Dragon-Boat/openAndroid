package com.sundae.zl.openandroid.fragment.multiTypeDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sundae.zl.openandroid.R;
import com.sundae.zl.openandroid.fragment.BaseFragment;
/**
 * Created by @author hzzhoulong
 * on 2017-8-23.
 * # Copyright 2017 netease. All rights reserved.
 */

public class ShopInfoFragment extends BaseFragment {

	ViewPagerItemBinder.ShopDiscount shopDiscount;
	RecyclerView shopInfoRV;
	RecyclerView.Adapter adapter;

	public static Fragment instance(ViewPagerItemBinder.ShopDiscount shopDiscount) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("key", shopDiscount);
		ShopInfoFragment fragment = new ShopInfoFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	class BaseAdapter extends RecyclerView.Adapter {
		ViewPagerItemBinder.ShopDiscount shopDiscount;

		public BaseAdapter(ViewPagerItemBinder.ShopDiscount shopDiscount) {
			this.shopDiscount = shopDiscount;
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new BaseVH(new TextView(getContext()));
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
			((BaseVH) holder).render(shopDiscount.shopName.get(position));

		}

		@Override
		public int getItemCount() {
			return shopDiscount != null ? shopDiscount.shopName.size() : 0;
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.shop_info_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		shopDiscount = (ViewPagerItemBinder.ShopDiscount) getArguments().getSerializable("key");
		shopInfoRV = (RecyclerView) view.findViewById(R.id.shop_info_rv);
		shopInfoRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

		adapter = new BaseAdapter(shopDiscount);

		shopInfoRV.setAdapter(adapter);

	}
}

class BaseVH extends RecyclerView.ViewHolder {

	TextView textView;

	public BaseVH(View itemView) {
		super(itemView);
		textView = ((TextView) itemView);
	}

	void render(ViewPagerItemBinder.ShopInfo shopInfo) {
		textView.setText(shopInfo.name);
	}
}

