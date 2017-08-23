package com.sundae.zl.openandroid.fragment.multiTypeDemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sundae.zl.openandroid.R;
import com.sundae.zl.openandroid.fragment.BaseUtilFragment;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
/**
 * Created by @author hzzhoulong
 * on 2017-8-14.
 * # Copyright 2017 netease. All rights reserved.
 */

public class MultiTypeFragment extends BaseUtilFragment {
	RecyclerView recyclerView;
	MultiTypeAdapter adapter;

	public static MultiTypeFragment instance() {
		return new MultiTypeFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.multi_type,container,false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		recyclerView = $(view, R.id.recyclerView);
		initRecyclerView(recyclerView);
		adapter = new MultiTypeAdapter();
		adapter.register(Banner.class, new BannerViewBinder());
		adapter.register(SectionViewBinder.Section.class, new SectionViewBinder());
		adapter.register(ViewPagerItemBinder.ShopDiscount.class, new ViewPagerItemBinder());

		recyclerView.setAdapter(adapter);

		Banner banner = new Banner();
		banner.content = "Android";
		banner.picUrl = "http://clipart-library.com/images/gie575bkT.jpg";
		banner.linkUrl = "";
		Items items = new Items();
		items.add(banner);

		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
			}
		});

		SectionViewBinder.Section section = new SectionViewBinder.Section();
		section.title = "信用卡优惠";
		section.location = "杭州";

		SectionViewBinder.Section section2 = new SectionViewBinder.Section();
		section2.title = "玩转信用卡";
		section2.location = "";

		items.add(section);
		items.add(section2);

		ViewPagerItemBinder.ShopDiscount shopDiscount = new ViewPagerItemBinder.ShopDiscount();
		List<ViewPagerItemBinder.ShopInfo> infos = new ArrayList<>();
		for (int i = 0; i < 80; i++) {
			ViewPagerItemBinder.ShopInfo shopInfo = new ViewPagerItemBinder.ShopInfo();
			shopInfo.name = "海底捞 : " + i;
			infos.add(shopInfo);
		}
		shopDiscount.shopName = infos;
		items.add(shopDiscount);


		adapter.setItems(items);
		adapter.notifyDataSetChanged();
	}

	private void initRecyclerView(RecyclerView recyclerView) {
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
	}


	class Banner{
		String content;
		String picUrl;
		String linkUrl;
	}
	class BannerViewBinder extends ItemViewBinder<Banner,BannerViewBinder.BannerVH>{
		class BannerVH extends RecyclerView.ViewHolder{
			ImageView imageView;
			TextView textView;
			BannerVH(View itemView) {
				super(itemView);
				imageView = $(itemView, R.id.bannerIv);
				textView = $(itemView, R.id.bannerTv);

				itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(v.getContext(), "click banner", Toast.LENGTH_SHORT).show();
					}
				});
			}

			void render(Banner banner) {
				Glide.with(getContext()).load(banner.picUrl).into(imageView);
				textView.setText(banner.content);
			}
		}

		@NonNull
		@Override
		protected BannerVH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
			return new BannerVH(inflater.inflate(R.layout.multi_type_banner, parent, false));
		}

		@Override
		protected void onBindViewHolder(@NonNull BannerVH holder, @NonNull Banner item) {
			holder.render(item);
		}
	}
}
