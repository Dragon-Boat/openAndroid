package com.sundae.zl.openandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sundae.zl.openandroid.R;
import com.sundae.zl.openandroid.fragment.multiTypeDemo.ShopInfoFragment;
import com.sundae.zl.openandroid.fragment.multiTypeDemo.ViewPagerItemBinder;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by @author hzzhoulong
 * on 2017-8-23.
 * # Copyright 2017 netease. All rights reserved.
 */

public class CoordinatorLayoutFragment extends BaseUtilFragment {
	public static Fragment instance() {
		return new CoordinatorLayoutFragment();
	}

	TabLayout tabLayout;
	 ShopVPAdapter adapter;
	ViewPager viewPager;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.coordinator_layout_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		tabLayout = (TabLayout) view.findViewById(R.id.shop_tab);
		viewPager = (ViewPager) view.findViewById(R.id.shop_viewpager);

		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
		tabLayout.setupWithViewPager(viewPager);
		adapter = new ShopVPAdapter(getFragmentManager());
		viewPager.setAdapter(adapter);

		adapter.update();

	}

	class ShopVPAdapter extends FragmentPagerAdapter{
		ViewPagerItemBinder.ShopDiscount shopDiscount;

		public void update() {
			notifyDataSetChanged();
		}

		public ViewPagerItemBinder.ShopDiscount getShopDiscount(int size){
			ViewPagerItemBinder.ShopDiscount shopDiscount = new ViewPagerItemBinder.ShopDiscount();
			List<ViewPagerItemBinder.ShopInfo> infos = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				ViewPagerItemBinder.ShopInfo shopInfo = new ViewPagerItemBinder.ShopInfo();
				shopInfo.name = "海底捞 : " + i;
				infos.add(shopInfo);
			}
			shopDiscount.shopName = infos;
			return shopDiscount;
		}

		String[] pagerTitle = new String[]{
				"餐饮",
				"电影",
				"购物",
				"商旅",
				"用车"
		};

		public ShopVPAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return ShopInfoFragment.instance(getShopDiscount((1 + position) * 10));
		}

		@Override
		public int getCount() {
			return pagerTitle.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return pagerTitle[position];
		}
	}
}
