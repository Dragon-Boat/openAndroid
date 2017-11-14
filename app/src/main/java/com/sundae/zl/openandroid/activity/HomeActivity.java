package com.sundae.zl.openandroid.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.sundae.zl.openandroid.R;
import com.sundae.zl.openandroid.fragment.AgentWebFragment;
import com.sundae.zl.openandroid.fragment.CoordinatorLayoutFragment;
import com.sundae.zl.openandroid.fragment.DataBindingFragment;
import com.sundae.zl.openandroid.fragment.LocationFragment;
import com.sundae.zl.openandroid.fragment.ScanViewFragment;
import com.sundae.zl.openandroid.fragment.SwapAdapterDemoFragment;
import com.sundae.zl.openandroid.fragment.WebViewDemoFragment;
import com.sundae.zl.openandroid.fragment.multiTypeDemo.MultiTypeFragment;
import com.sundae.zl.openandroid.fragment.similarPic.SimilarPicFragment;
import com.sundae.zl.openandroid.utils.PhotoInfo;
import com.sundae.zl.openandroid.utils.PhotoUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

public class HomeActivity extends BaseUtilActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		$(R.id.swapAdapter).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getSupportFragmentManager().beginTransaction()
						.add(android.R.id.content, SwapAdapterDemoFragment.instance(), "")
						.addToBackStack("").commit();
			}
		});

		$(R.id.webViewAndJs).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getSupportFragmentManager().beginTransaction()
						.add(android.R.id.content, WebViewDemoFragment.instance(), "")
						.addToBackStack("").commit();
			}
		});

		$(R.id.multiTypeDemo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getSupportFragmentManager().beginTransaction()
						.add(android.R.id.content, MultiTypeFragment.instance(), "")
						.addToBackStack("").commit();
			}
		});
		$(R.id.dataBindingDemo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getSupportFragmentManager().beginTransaction()
						.add(android.R.id.content, DataBindingFragment.instance(), "")
						.addToBackStack("").commit();
			}
		});
		$(R.id.CoordinatorLayoutDemo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getSupportFragmentManager().beginTransaction()
						.add(android.R.id.content, CoordinatorLayoutFragment.instance(), "")
						.addToBackStack("").commit();
			}
		});
		$(R.id.AgentWebDemo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getSupportFragmentManager().beginTransaction()
						.add(android.R.id.content, AgentWebFragment.instance(), "")
						.addToBackStack("").commit();
			}
		});

		$(R.id.LocationDemo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getSupportFragmentManager().beginTransaction()
						.add(android.R.id.content, LocationFragment.instance(), "")
						.addToBackStack("").commit();
			}
		});

		$(R.id.SimilarPicDemo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getSupportFragmentManager().beginTransaction()
						.add(android.R.id.content, SimilarPicFragment.instance(), "")
						.addToBackStack("").commit();
			}
		});

		$(R.id.ScanViewDemo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getSupportFragmentManager().beginTransaction()
						.add(android.R.id.content, ScanViewFragment.instance(), "")
						.addToBackStack("").commit();
			}
		});

		AndPermission.with(this)
				.permission(Permission.STORAGE)
				.callback(new PermissionListener() {
					@Override
					public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
						List<PhotoInfo> photoInfos = PhotoUtils.getScreenShotFiles(HomeActivity.this, null, 100);
						if (photoInfos != null && photoInfos.size() > 0) {
							for (PhotoInfo photoInfo : photoInfos) {
								Log.d("zl", "info:" + photoInfo.toString());
							}
						}
					}

					@Override
					public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {

					}
				})
				.start();


	}

	@Override
	public void onClick(View v) {
	}
}
