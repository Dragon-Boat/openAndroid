package com.sundae.zl.openandroid.activity;

import android.os.Bundle;
import android.view.View;

import com.sundae.zl.openandroid.R;
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
				getFragmentManager().beginTransaction()
						.add(android.R.id.content, SwapAdapterDemoFragment.instance(), "")
						.addToBackStack("").commit();
			}
		});

		$(R.id.webViewAndJs).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().beginTransaction()
						.add(android.R.id.content, WebViewDemoFragment.instance(), "")
						.addToBackStack("").commit();
			}
		});
	}

	@Override
	public void onClick(View v) {
	}
}
