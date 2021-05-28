package com.libo.webview.webviewprocess.webchromeclient;

import android.util.Log;


import com.libo.webview.WebViewCallBack;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/28 11:50 上午
 * @describe:
 */
public  class LiboWebChromeClient extends WebChromeClient {
    private WebViewCallBack  mWebViewCallBack;
    public static String TAG="LiboWebChromeClient";

    public LiboWebChromeClient(WebViewCallBack mWebViewCallBack) {
        this.mWebViewCallBack = mWebViewCallBack;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if(mWebViewCallBack != null) {
            mWebViewCallBack.updateTitle(title);
        } else {
            Log.e(TAG, "WebViewCallBack is null.");
        }
    }
    /**
     * Report a JavaScript console message to the host application. The ChromeClient
     * should override this to process the log message as they see fit.
     * @param consoleMessage Object containing details of the console message.
     * @return {@code true} if the message is handled by the client.
     */
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        // Call the old version of this function for backwards compatability.
        Log.d(TAG, consoleMessage.message());
        return super.onConsoleMessage(consoleMessage);
    }
}
