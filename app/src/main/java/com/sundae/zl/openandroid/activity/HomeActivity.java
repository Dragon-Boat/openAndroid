package com.sundae.zl.openandroid.activity;

import android.os.Bundle;
import android.view.View;

import com.sundae.zl.openandroid.R;
import com.sundae.zl.openandroid.fragment.AgentWebFragment;
import com.sundae.zl.openandroid.fragment.BitMapFragment;
import com.sundae.zl.openandroid.fragment.CoordinatorLayoutFragment;
import com.sundae.zl.openandroid.fragment.DataBindingFragment;
import com.sundae.zl.openandroid.fragment.LocationFragment;
import com.sundae.zl.openandroid.fragment.multiTypeDemo.MultiTypeFragment;
import com.sundae.zl.openandroid.fragment.SwapAdapterDemoFragment;
import com.sundae.zl.openandroid.fragment.WebViewDemoFragment;

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

		$(R.id.BitmapDemo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getSupportFragmentManager().beginTransaction()
						.add(android.R.id.content, BitMapFragment.instance(), "")
						.addToBackStack("").commit();
			}
		});
	}

	@Override
	public void onClick(View v) {
	}
}
