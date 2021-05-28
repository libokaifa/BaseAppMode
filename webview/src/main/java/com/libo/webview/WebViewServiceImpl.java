package com.libo.webview;

import android.content.Context;
import android.content.Intent;

import com.google.auto.service.AutoService;
import com.libo.common.autoservice.IWebViewService;
import com.libo.webview.utils.Constants;

import androidx.fragment.app.Fragment;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/28 3:02 下午
 * @describe:
 */
@AutoService(IWebViewService.class)
public class WebViewServiceImpl  implements IWebViewService {
    @Override
    public void startWebViewActivity(Context context, String url, String title, boolean isShowActionBar) {
        if (context != null) {
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(Constants.TITLE, title);
            intent.putExtra(Constants.URL, url);
            intent.putExtra(Constants.IS_SHOW_ACTION_BAR, isShowActionBar);
            context.startActivity(intent);
        }
    }

    @Override
    public Fragment getWebViewFragment(String url, boolean canNativeRefresh) {
        return WebViewFragment.newInstance(url, canNativeRefresh);
    }

    @Override
    public void startDemoHtml(Context context) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Constants.TITLE, "本地Demo测试页");
        intent.putExtra(Constants.URL, Constants.ANDROID_ASSET_URI + "demo.html");
        context.startActivity(intent);
    }
}
