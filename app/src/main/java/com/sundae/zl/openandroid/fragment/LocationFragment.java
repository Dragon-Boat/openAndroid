package com.sundae.zl.openandroid.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.sundae.zl.openandroid.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;
/**
 * Created by @author hzzhoulong
 * on 2017-9-22.
 * # Copyright 2017 netease. All rights reserved.
 */

public class LocationFragment extends BaseUtilFragment {
	private static final int MIN_TIME = 0;
	private static final int MIN_DISTANCE = 0;
	private static final String TAG = "location";
	private static Location location;
	TextView textView,textView2,textView3;
	private LocationListener listener;
	private GpsStatus.NmeaListener nmeaListener;
	private android.location.LocationManager mLocationManager;

	public static Fragment instance() {
		return new LocationFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.logintion_fragment, container, false);
	}

	@Override
	public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		textView = (TextView) view.findViewById(R.id.text1);
		textView2 = $(view, R.id.text2);
		textView3 = $(view, R.id.text3);
		nmeaListener = new GpsStatus.NmeaListener() {
			@Override
			public void onNmeaReceived(long timestamp, String nmea) {
				Log.d(TAG, "onNmeaReceived: " + nmea);
				textView3.setText("nmea:" + nmea + ", timestamp:" + timestamp);
			}
		};
		listener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				LocationFragment.location = location;
				if (location != null) {
					Log.i(TAG, "onLocationChanged: " + location.toString());

				}
				textView.setText(location != null ? location.toString() : "null");

			}

			@Override
			public void onStatusChanged(String s, int i, Bundle bundle) {
				Log.i(TAG, "onStatusChanged: " + s);
			}

			@Override
			public void onProviderEnabled(String s) {
				Log.i(TAG, "onProviderEnabled: " + s);
			}

			@Override
			public void onProviderDisabled(String s) {
				Log.i(TAG, "onProviderDisabled: " + s);
			}
		};
		mLocationManager = (android.location.LocationManager) getActivity()
				.getSystemService(Context.LOCATION_SERVICE);


		AndPermission.with(this)
				.permission(Permission.LOCATION)
				.callback(new PermissionListener() {
					@Override
					public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
						if (ActivityCompat.checkSelfPermission(getContext(),
								android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager
								.PERMISSION_GRANTED ||
								ActivityCompat.checkSelfPermission(getContext(),
										android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
							if (mLocationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
								((TextView) view.findViewById(R.id.gps)).setText("gps enabled");

								location = mLocationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
								listener.onLocationChanged(location);
								mLocationManager.addNmeaListener(nmeaListener);
								mLocationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER,
										MIN_TIME, MIN_DISTANCE, listener);
							}
							if (mLocationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
								((TextView) view.findViewById(R.id.net)).setText("net enabled");

								location = mLocationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);
								listener.onLocationChanged(location);
								mLocationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER,
										MIN_TIME, MIN_DISTANCE, listener);
								mLocationManager.addNmeaListener(nmeaListener);
							}

							AMapLocationClient client = new AMapLocationClient(getContext());
							client.setLocationListener(new AMapLocationListener() {
								@Override
								public void onLocationChanged(AMapLocation aMapLocation) {
									if (aMapLocation != null) {
										Log.i(TAG, "onLocationChanged: " + aMapLocation.toString());
									}
									textView2.setText(aMapLocation != null ? aMapLocation.toString() : "null");
								}
							});
							client.startLocation();
						}





					}

					@Override
					public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {

					}
				}).start();


	}
}
