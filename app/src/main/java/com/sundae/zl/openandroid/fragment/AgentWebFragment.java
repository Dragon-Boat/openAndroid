package com.sundae.zl.openandroid.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;
import com.just.library.AgentWebSettings;
import com.just.library.ChromeClientCallbackManager;
import com.just.library.WebDefaultSettingsManager;
import com.sundae.zl.openandroid.R;
/**
 * Created by @author hzzhoulong
 * on 2017-8-24.
 * # Copyright 2017 netease. All rights reserved.
 */

public class AgentWebFragment extends BaseUtilFragment {

	AgentWeb agentWeb;
	public static Fragment instance(){
		return new AgentWebFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.agent_web_fragment, container, false);
	}
	protected AgentWeb mAgentWeb;
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mAgentWeb = AgentWeb.with(this)//
				.setAgentWebParent((ViewGroup) view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件
				.setIndicatorColorWithHeight(-1, 2)//设置进度条颜色与高度-1为默认值，2单位为dp
				.setAgentWebWebSettings(getSettings())//设置 AgentWebSettings
				.setWebViewClient(mWebViewClient)//WebViewClient ， 与WebView 一样
				.setWebChromeClient(mWebChromeClient) //WebChromeClient
				.setReceivedTitleCallback(mCallback)//标题回调
				.setSecurityType(AgentWeb.SecurityType.strict) //严格模式
				.createAgentWeb()//创建AgentWeb
				.ready()//设置 WebSettings
				.go(getUrl()); //WebView载入该url地址的页面并显示。





	}

	public AgentWebSettings getSettings() {
		return WebDefaultSettingsManager.getInstance();
	}
	public static final String URL_KEY = "url_key";
	public String getUrl() {
		String target = "";

//		if (TextUtils.isEmpty(target = this.getArguments().getString(URL_KEY))) {
//			target = "http://www.jd.com";
//		}
		return "http://www.jd.com";
	}


	protected ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
		@Override
		public void onReceivedTitle(WebView view, String title) {


		}
	};
	protected WebChromeClient mWebChromeClient = new WebChromeClient() {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			//  super.onProgressChanged(view, newProgress);
			//Log.i(TAG,"onProgressChanged:"+newProgress+"  view:"+view);
		}
	};
	protected WebViewClient mWebViewClient = new WebViewClient() {

		@Override
		public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
			super.onReceivedError(view, request, error);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
			return shouldOverrideUrlLoading(view, request.getUrl() + "");
		}


		//
		@Override
		public boolean shouldOverrideUrlLoading(final WebView view, String url) {
			//intent:// scheme的处理 如果返回false ， 则交给 DefaultWebClient 处理 ， 默认会打开该Activity  ， 如果Activity不存在则跳到应用市场上去.  true 表示拦截
			//例如优酷视频播放 ，intent://play?...package=com.youku.phone;end;
			//优酷想唤起自己应用播放该视频 ， 下面拦截地址返回 true  则会在应用内 H5 播放 ，禁止优酷唤起播放该视频， 如果返回 false ， DefaultWebClient  会根据intent 协议处理 该地址 ， 首先匹配该应用存不存在 ，如果存在 ， 唤起该应用播放 ， 如果不存在 ， 则跳到应用市场下载该应用 .
			if (url.startsWith("intent://") && url.contains("com.youku.phone"))
				return true;
            /*else if (isAlipay(view, url))   //1.2.5开始不用调用该方法了 ，只要引入支付宝sdk即可 ， DefaultWebClient 默认会处理相应url调起支付宝
                return true;*/


			return false;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {



		}
	};

}
