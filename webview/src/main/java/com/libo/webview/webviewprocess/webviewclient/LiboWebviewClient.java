package com.libo.webview.webviewprocess.webviewclient;

import android.graphics.Bitmap;
import android.util.Log;


import com.libo.webview.WebViewCallBack;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/28 1:28 下午
 * @describe:
 */
public class LiboWebviewClient  extends WebViewClient {
    private WebViewCallBack mWebViewCallBack;
    private static final String TAG = "XiangxueWebViewClient";

    public LiboWebviewClient(WebViewCallBack mWebViewCallBack) {
        this.mWebViewCallBack = mWebViewCallBack;
    }
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if(mWebViewCallBack != null) {
            mWebViewCallBack.pageStarted(url);
        } else {
            Log.e(TAG, "WebViewCallBack is null.");
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if(mWebViewCallBack != null) {
            mWebViewCallBack.pageFinished(url);
        } else {
            Log.e(TAG, "WebViewCallBack is null.");
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if(mWebViewCallBack != null) {
            mWebViewCallBack.onError();
        } else {
            Log.e(TAG, "WebViewCallBack is null.");
        }
    }
}
