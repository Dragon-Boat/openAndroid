package com.sundae.zl.openandroid.fragment.similarPic;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sundae.zl.openandroid.R;
import com.sundae.zl.openandroid.fragment.BaseUtilFragment;
/**
 * Created by @author hzzhoulong
 * on 2017-10-14.
 * # Copyright 2017 netease. All rights reserved.
 */

public class SimilarPicFragment extends BaseUtilFragment implements View.OnClickListener {
	private static final String TAG = "SimilarPicFragment";
	TextView corTv,vsTv,resultTv;
	Button captureBtn;
	RectView rectView;
	ImageView sourceIv, sampleIv;
	boolean isSampleExist = false;
	Rect curRect;
	Bitmap sampleBitmap, sourceBitmap;

	public static Fragment instance() {
		return new SimilarPicFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.similar_pic_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		corTv = $(view, R.id.cor_tv);
		rectView = $(view, R.id.rect_view);
		captureBtn = $(view, R.id.capture_btn);
		sourceIv = $(view, R.id.source_iv);
		sampleIv = $(view, R.id.sample_iv);
		vsTv = $(view, R.id.vs_tv);
		vsTv.setOnClickListener(this);
		resultTv = $(view, R.id.result_tv);

		rectView.setListener(new RectView.Listener() {
			@Override
			public void onRect(Rect rect) {
				corTv.setText("坐标: " + rect.flattenToString());
				curRect = rect;
			}
		});

		captureBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (curRect == null) {
					Toast.makeText(v.getContext(), "先选择截图区域（移动）", Toast.LENGTH_SHORT).show();
					return;
				}

				areaCapture(curRect);

			}
		});

	}

	private void areaCapture(Rect curRect) {

		Bitmap bitmap = getCacheBitmapFromView(getView());
		int x = curRect.left;
		int y = curRect.top;
		Bitmap captureBitmap = Bitmap.createBitmap(bitmap, x, y- getStatusHeight(), curRect.width(), curRect.height() );

		if (!isSampleExist) {
			sampleIv.setImageBitmap(captureBitmap);
			sampleBitmap = captureBitmap;
			isSampleExist = true;
		} else {
			sourceBitmap = captureBitmap;
			sourceIv.setImageBitmap(captureBitmap);
		}

	}

	/**
	 * 获取一个 View 的缓存视图
	 *
	 * @param view
	 * @return
	 */
	private Bitmap getCacheBitmapFromView(View view) {
		final boolean drawingCacheEnabled = true;
		view.setDrawingCacheEnabled(drawingCacheEnabled);
		view.buildDrawingCache(drawingCacheEnabled);
		final Bitmap drawingCache = view.getDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
		if (drawingCache != null) {
			bitmap = Bitmap.createBitmap(drawingCache);
			view.setDrawingCacheEnabled(false);
		}
		return bitmap;
	}


	private int getStatusHeight() {
		int statusBarHeight1 = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			//根据资源ID获取响应的尺寸值
			statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
		}
		return statusBarHeight1;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.vs_tv:
				if (sampleBitmap == null || sourceBitmap == null) {
					resultTv.setText("还没有截图");
					return;
				} else {
					int result = SimilarPicUtils.similarDetect(sourceBitmap, sampleBitmap, 8, 8);
					resultTv.setText("比较结果:" + result);
				}
				break;
		}
	}
}
