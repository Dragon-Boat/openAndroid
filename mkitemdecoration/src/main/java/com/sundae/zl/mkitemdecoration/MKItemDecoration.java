package com.sundae.zl.mkitemdecoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by @author hzzhoulong
 * on 2017-9-28.
 * # Copyright 2017 netease. All rights reserved.
 */

public class MKItemDecoration extends RecyclerView.ItemDecoration {

	private Drawable mDivider;

	private TextPaint textPaint;

	private Builder builder;

	public MKItemDecoration(@NonNull Builder builder) {
		this.builder = builder;
		textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		if (builder.drawable != null) {
			mDivider = builder.drawable;
		} else {
			mDivider = new ColorDrawable(builder.decorationColor);
		}
		textPaint.setTextSize(builder.textSize);
		textPaint.setColor(builder.textColor);

	}

	public static class Builder {
		// 整体高度
		private int decorationHeight;
		// 背景色
		private int decorationColor;
		// 分组悬停功能接口
		private IHover iHover;
		// 分割线开始绘制的位置，一般等于头部数量
		private int itemOffset;

		// 分组时显示的文本大小
		private int textSize;
		// 分组时显示的文本颜色
		private int textColor;
		// 分组时显示的文本距离左侧的距离
		private int textLeftPadding;
		// 自定义的背景drawable
		private Drawable drawable;

		public Builder drawable(Drawable drawable) {
			this.drawable = drawable;
			return this;
		}

		public Builder height(int decorationHeight) {
			this.decorationHeight = decorationHeight;
			return this;
		}

		public Builder color(@ColorInt int decorationColor) {
			this.decorationColor = decorationColor;
			return this;
		}

		public Builder iHover(IHover iHover) {
			this.iHover = iHover;
			return this;
		}

		public Builder itemOffset(int itemOffset) {
			this.itemOffset = itemOffset;
			return this;
		}

		public Builder textSize( int textSize) {
			this.textSize = textSize;
			return this;
		}

		public Builder textColor(@ColorInt int textColor) {
			this.textColor = textColor;
			return this;
		}

		public Builder textLeftPadding( int textLeftPadding) {
			this.textLeftPadding = textLeftPadding;
			return this;
		}

		public MKItemDecoration build() {
			return new MKItemDecoration(this);
		}


	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		super.onDraw(c, parent, state);
		if (!(parent.getLayoutManager() instanceof LinearLayoutManager)) {
			return;
		}
		// 目前只支持纵向
		if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL) {
			drawVertical(c, parent);
		}

	}

	private void drawVertical(Canvas c, RecyclerView parent) {
		int bottom, top;
		final int left = parent.getPaddingLeft();
		final int right = parent.getWidth() - parent.getPaddingRight();

		int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View childView = parent.getChildAt(i);
			int position = parent.getChildAdapterPosition(childView);

			// 跳过需要偏移的item
			if (position < builder.itemOffset) {
				continue;
			}
			if (builder.iHover == null) {
				// 普通分割线时，最后一个item也跳过
				if (position == parent.getAdapter().getItemCount() - 1) {
					continue;
				}
				// 在item底部绘制一个指定分割线
				top = childView.getBottom();
				bottom = top + builder.decorationHeight;
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(c);

			} else {
				// 分组时，在item顶部绘制
				if (builder.iHover.isGroup(position)) {
					bottom = childView.getTop();
					top = bottom - builder.decorationHeight;
					mDivider.setBounds(left, top, right, bottom);
					mDivider.draw(c);
					String text = builder.iHover.groupText(position);
					if (!TextUtils.isEmpty(text)) {
						Paint.FontMetrics fm = textPaint.getFontMetrics();
						//文字竖直居中显示
						float baseLine = bottom - (builder.decorationHeight - (fm.bottom - fm.top)) / 2 - fm.bottom;
						c.drawText(text, left + builder.textLeftPadding, baseLine, textPaint);
					}
				}
			}
		}
	}

	@Override
	public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
		super.onDrawOver(c, parent, state);

		// 只有需要分组功能时，才走以下逻辑
		if (builder.iHover != null) {
			int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
			if (position <= -1 || position >= parent.getAdapter().getItemCount() - 1) {
				// 越界检查
				return;
			}
			// 寻找即将成为第一个可见item的child
			View child = parent.findViewHolderForLayoutPosition(position + 1).itemView;

			boolean flag = false;
			if (builder.iHover.isGroup(position + 1)) {
				int dy = child.getTop() - builder.decorationHeight * 2;
				// 分组栏移动效果
				if (dy <= 0) {
					c.save();
					c.translate(0, dy);
					flag = true;
				}
			}

			int bottom, top;
			final int left = parent.getPaddingLeft();
			final int right = parent.getWidth() - parent.getPaddingRight();

			top = parent.getPaddingTop();
			bottom = top + builder.decorationHeight;
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
			String text = builder.iHover.groupText(position);
			if (!TextUtils.isEmpty(text)) {
				Paint.FontMetrics fm = textPaint.getFontMetrics();
				//文字竖直居中显示
				float baseLine = bottom - (builder.decorationHeight - (fm.bottom - fm.top)) / 2 - fm.bottom;
				c.drawText(text, left + builder.textLeftPadding, baseLine, textPaint);
			}
			if (flag) {
				c.restore();
			}
		}
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);
		int pos = parent.getChildAdapterPosition(view);

		if (pos < builder.itemOffset) {
			outRect.set(0, 0, 0, 0);
			return;
		}

		if (builder.iHover == null) {
			// 普通分割线不绘制最后一个item
			if (pos == parent.getAdapter().getItemCount() - 1) {
				outRect.set(0, 0, 0, 0);
			} else {
				outRect.set(0,0,0,builder.decorationHeight);
			}
		} else {
			// 分组模式只在分组时才绘制
			if (builder.iHover.isGroup(pos)) {
				outRect.set(0, builder.decorationHeight, 0, 0);
			}
		}

	}
}
