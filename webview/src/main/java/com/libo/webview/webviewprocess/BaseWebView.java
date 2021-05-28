package com.libo.webview.webviewprocess;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;


import com.alibaba.fastjson.JSON;
import com.libo.common.logger.LiboLoger;
import com.libo.webview.WebViewCallBack;
import com.libo.webview.bean.JsParam;
import com.libo.webview.webviewprocess.settings.WebViewDefaultSettings;
import com.libo.webview.webviewprocess.webchromeclient.LiboWebChromeClient;
import com.libo.webview.webviewprocess.webviewclient.LiboWebviewClient;
import com.tencent.smtt.sdk.WebView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/28 11:13 上午
 * @describe:
 */
public class BaseWebView extends WebView {
    public static String TAG="BaseWebView";
    public BaseWebView(@NonNull Context context) {
        super(context);
        init();
    }


    public BaseWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @SuppressLint("JavascriptInterface")
    private void init() {
        WebViewProcessCommandDispatcher.getInstance().initAidlConnection();
        WebViewDefaultSettings.getInstance().setSettings(this);
        addJavascriptInterface(this, "libowebview");
    }

    @JavascriptInterface
    public void takeNativeAction(final String jsParam) {
        LiboLoger.logJson(jsParam);
        if (!TextUtils.isEmpty(jsParam)) {
            final JsParam jsParamObject = JSON.parseObject(jsParam,JsParam.class);
            if (jsParamObject != null) {
                WebViewProcessCommandDispatcher.getInstance().executeCommand(jsParamObject.name,JSON.toJSONString(jsParamObject.param), this);
            }
        }
    }

    public void registerWebViewCallBack(WebViewCallBack webViewCallBack) {
        setWebViewClient(new LiboWebviewClient(webViewCallBack));
        setWebChromeClient(new LiboWebChromeClient(webViewCallBack));
    }
    public void handleCallback(final String callbackname, final String response) {
        if (!TextUtils.isEmpty(callbackname) && !TextUtils.isEmpty(response)) {
            post(new Runnable() {
                @Override
                public void run() {
                    String jscode = "javascript:libo.callback('" + callbackname + "'," + response + ")";
                    Log.e("xxxxxx", jscode);
                    evaluateJavascript(jscode, null);
                }
            });
        }
    }
}
