package com.sundae.zl.openandroid.fragment.multiTypeDemo;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sundae.zl.openandroid.R;
import com.sundae.zl.openandroid.activity.BaseActivity;

import java.io.Serializable;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by @author hzzhoulong
 * on 2017-8-23.
 * # Copyright 2017 netease. All rights reserved.
 */

public class ViewPagerItemBinder extends ItemViewBinder<ViewPagerItemBinder.ShopDiscount,ViewPagerItemBinder.ViewPagerVH> {
	public static class ShopDiscount implements Serializable{
		public List<ShopInfo> shopName;

	}
	public static class ShopInfo{
		public String name;
	}

	public class ViewPagerVH extends RecyclerView.ViewHolder {
		TabLayout tabLayout;
		ViewPager viewPager;
		shopVPAdapter adapter ;
		public ViewPagerVH(View itemView) {
			super(itemView);
			tabLayout = (TabLayout) itemView.findViewById(R.id.shop_discount_tab);
			tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
			viewPager = (ViewPager) itemView.findViewById(R.id.shop_discount_vp);
			tabLayout.setupWithViewPager(viewPager);
			adapter = new shopVPAdapter(((BaseActivity) itemView.getContext()).getSupportFragmentManager());
			viewPager.setAdapter(adapter);

		}

		void render(ShopDiscount shopDiscount) {
			adapter.setShopDiscount(shopDiscount);

		}
		class shopVPAdapter extends FragmentPagerAdapter{
			ShopDiscount shopDiscount;

			public void setShopDiscount(ShopDiscount shopDiscount) {
				this.shopDiscount = shopDiscount;
				notifyDataSetChanged();
			}

			String[] pagerTitle = new String[]{
					"餐饮",
					"电影",
					"购物"
			};
			public shopVPAdapter(FragmentManager fm) {
				super(fm);
			}

			@Override
			public Fragment getItem(int position) {
				return ShopInfoFragment.instance(shopDiscount);
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

	@NonNull
	@Override
	protected ViewPagerVH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
		return new ViewPagerVH(inflater.inflate(R.layout.multi_type_viewpager, parent, false));
	}

	@Override
	protected void onBindViewHolder(@NonNull ViewPagerVH holder, @NonNull ShopDiscount item) {
		holder.render(item);

	}
}
