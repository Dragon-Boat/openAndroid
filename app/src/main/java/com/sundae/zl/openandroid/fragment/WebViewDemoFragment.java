package com.sundae.zl.openandroid.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sundae.zl.openandroid.R;
/**
 * Created by @author hzzhoulong
 * on 2017-8-14.
 * # Copyright 2017 netease. All rights reserved.
 */

public class WebViewDemoFragment extends BaseUtilFragment {
	private static final String TAG = "WebViewDemoFragment";
	WebView webView;

	public static WebViewDemoFragment instance() {
		return new WebViewDemoFragment();
	}

	class FundEO {
		long _id;
		String fundName;
		String iconUrl;

		@Override
		@JavascriptInterface
		public String toString() {
			return "FundEO{" +
					"_id=" + _id +
					", fundName='" + fundName + '\'' +
					", iconUrl='" + iconUrl + '\'' +
					'}';
		}

		@JavascriptInterface
		public long get_id() {
			return _id;
		}

		@JavascriptInterface
		public String getFundName() {
			return fundName;
		}

		@JavascriptInterface
		public String getIconUrl() {
			return iconUrl;
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.webview_demo, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		webView = $(view, R.id.web_view);
		initWebView(webView);

		webView.loadUrl("file:///android_asset/test.html");

		$(view, R.id.tell_js_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				webView.evaluateJavascript("javascript:showText(\"" + getActivity().getClass().getSimpleName() + "\")", new ValueCallback<String>() {
					@Override
					public void onReceiveValue(String value) {
						Log.d(TAG, "onReceiveValue: " + value);
					}
				});
			}
		});
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView(WebView webView) {
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		webView.addJavascriptInterface(this, "android");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (webView != null) {
			webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
			webView.clearHistory();

			((ViewGroup) webView.getParent()).removeView(webView);
			webView.destroy();
			webView = null;
		}
	}

	@JavascriptInterface
	public FundEO getFund() {
		FundEO fundEO = new FundEO();
		fundEO._id = (long) (Math.random() * 100);
		fundEO.fundName = getActivity().getLocalClassName() + 10 * Math.random();
		fundEO.iconUrl = "http://www.google.com/icon";

		return fundEO;
	}
}
