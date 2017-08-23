package com.sundae.zl.openandroid.fragment.multiTypeDemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sundae.zl.openandroid.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by @author hzzhoulong
 * on 2017-8-23.
 * # Copyright 2017 netease. All rights reserved.
 */

public class SectionViewBinder extends ItemViewBinder<SectionViewBinder.Section,SectionViewBinder.SectionVH> {

	public class SectionVH extends RecyclerView.ViewHolder{
		public TextView titleTv;
		public TextView locationTv;
		public SectionVH(View itemView) {
			super(itemView);
			titleTv = (TextView) itemView.findViewById(R.id.section_title);
			locationTv = (TextView) itemView.findViewById(R.id.section_location);
		}

		void render(Section section) {
			titleTv.setText(section.title);
			locationTv.setText(section.location);
		}
	}
	public static class Section{
		public String title;
		public String location;
	}

	@NonNull
	@Override
	protected SectionVH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
		return new SectionVH(inflater.inflate(R.layout.multi_type_section,parent,false));
	}

	@Override
	protected void onBindViewHolder(@NonNull SectionVH holder, @NonNull Section item) {
		holder.render(item);
	}
}
