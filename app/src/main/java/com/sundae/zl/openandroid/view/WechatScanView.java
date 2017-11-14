package com.sundae.zl.openandroid.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sundae.zl.openandroid.utils.BitmapUtil;
import com.sundae.zl.openandroid.utils.PhotoInfo;
import com.sundae.zl.openandroid.utils.ScreenUtil;

import java.util.List;

/**
 * Created by @author hzzhoulong
 * on 2017-10-23.
 * # Copyright 2017 netease. All rights reserved.
 */

public class WechatScanView extends View implements ValueAnimator.AnimatorUpdateListener {
	private static final int UP = 0;
	private static final int DOWN = 1;
	private final int scanLineColor = Color.parseColor("#ff595f");
	private final int scanLineHeight = (int) ScreenUtil.dp2px(2f);
	Bitmap bitmap;
	Rect sourceRect;
	private int bitmapTop;
	private int bitmapLeft;
	private int bitmapRight;
	private int bitmapBottom;
	private int scanLineLeft;
	private int scanLineRight;
	private int scanLineTop;
	private int scanLineBottom;
	private List<PhotoInfo> photoInfoList;
	private Paint scanLinePaint;
	private Rect bitmapRect;
	private Paint bitmapPaint;
	private int viewWidth;
	private int viewHeight;
	private BitmapFactory.Options options;
	private int inSampleSize;
	private Bitmap sampleBitmap;
	private float radio;
	private int photoIndex = 0;
	private int direction = DOWN;

	public WechatScanView(Context context) {
		this(context, null);
	}

	public WechatScanView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WechatScanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		scanLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		scanLinePaint.setColor(scanLineColor);
		scanLinePaint.setStyle(Paint.Style.FILL);
		options = new BitmapFactory.Options();

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		this.viewWidth = w;
		this.viewHeight = h;
		scanLineLeft = (int) ScreenUtil.dp2px(12.5f);
		scanLineRight = viewWidth - (int) ScreenUtil.dp2px(12.5f);
		scanLineTop = (int) ScreenUtil.dp2px(24.5f);
		scanLineBottom = scanLineTop + scanLineHeight;

		bitmapLeft = (int) ScreenUtil.dp2px(27.5f);
		bitmapRight = viewWidth - (int) ScreenUtil.dp2px(27.5f);
		bitmapTop = (int) ScreenUtil.dp2px(24.5f);
		bitmapBottom = viewHeight - ScreenUtil.dp2px(40);

		bitmapRect = new Rect(bitmapLeft, bitmapTop, bitmapRight, bitmapBottom);

		if (photoInfoList.isEmpty()) {
			return;
		}
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(photoInfoList.get(0).photoPath, options);
		inSampleSize = BitmapUtil.calculateInSampleSize(options, bitmapRect.width(), bitmapRect.height());
		options.inJustDecodeBounds = false;
		options.inSampleSize = inSampleSize;
		sampleBitmap = BitmapFactory.decodeFile(photoInfoList.get(0).photoPath, options);
		radio = (float) (bitmapRect.width() * 1.0 / sampleBitmap.getWidth());

		sourceRect = new Rect(0, 0, bitmapRect.width(), bitmapRect.height());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (photoInfoList.isEmpty()) {
			return;
		}


		if (direction == DOWN) {
			if (scanLineTop >= (bitmapRect.bottom - scanLineHeight)) {
				direction = UP;
			} else {
				scanLineTop += ScreenUtil.dp2px(2);
			}
		} else {
			if (scanLineTop <= ScreenUtil.dp2px(24.5f)) {
				direction = DOWN;
			} else {
				scanLineTop -= ScreenUtil.dp2px(2);
			}
		}

		if (bitmap == null) {
			sampleBitmap = BitmapFactory.decodeFile(photoInfoList.get(photoIndex).photoPath, options);
			bitmap = Bitmap.createScaledBitmap(sampleBitmap, bitmapRect.width(), (int) (radio * sampleBitmap.getHeight()), true);
		}

		sourceRect.bottom = sourceRect.top + bitmapRect.height();

		if (sourceRect.bottom >= bitmap.getHeight()) {
			sourceRect.top = 0;
			if (photoIndex >= photoInfoList.size() || photoIndex >= 10) {
				photoIndex = 0;
			} else {
				photoIndex++;
			}
			sampleBitmap = BitmapFactory.decodeFile(photoInfoList.get(photoIndex).photoPath, options);
			bitmap = Bitmap.createScaledBitmap(sampleBitmap, bitmapRect.width(), (int) (radio * sampleBitmap.getHeight()), true);
		} else {
			sourceRect.top += ScreenUtil.dp2px(0.5f);
		}

		scanLineBottom = scanLineTop + scanLineHeight;

		canvas.drawBitmap(bitmap, sourceRect, bitmapRect, bitmapPaint);

		canvas.drawRect(scanLineLeft, scanLineTop, scanLineRight, scanLineBottom, scanLinePaint);
		invalidate();
	}

	@Override
	public void onAnimationUpdate(ValueAnimator animation) {

	}

	public void setPhotoInfoList(List<PhotoInfo> photoInfoList) {
		this.photoInfoList = photoInfoList;
	}
}
