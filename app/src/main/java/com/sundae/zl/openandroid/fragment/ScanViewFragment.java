package com.sundae.zl.openandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sundae.zl.openandroid.R;
import com.sundae.zl.openandroid.utils.PhotoInfo;
import com.sundae.zl.openandroid.utils.PhotoUtils;
import com.sundae.zl.openandroid.view.WechatScanView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;
/**
 * Created by @author hzzhoulong
 * on 2017-10-23.
 * # Copyright 2017 netease. All rights reserved.
 */

public class ScanViewFragment extends BaseUtilFragment {
	public static Fragment instance() {
		return new ScanViewFragment();
	}

	@Override
	public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		AndPermission.with(this)
				.permission(Permission.STORAGE)
				.callback(new PermissionListener() {
					@Override
					public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
						List<PhotoInfo> photoInfos = PhotoUtils.getScreenShotFiles(getContext(), null, 100);
						if (photoInfos != null && photoInfos.size() > 0) {
							for (PhotoInfo photoInfo : photoInfos) {
								Log.d("zl", "info:" + photoInfo.toString());
							}
						}
						WechatScanView scanView = $(view, R.id.wechat_scan_view);
						scanView.setPhotoInfoList(photoInfos);
					}

					@Override
					public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {

					}
				})
				.start();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.scan_view_fragment, container, false);
	}
}
