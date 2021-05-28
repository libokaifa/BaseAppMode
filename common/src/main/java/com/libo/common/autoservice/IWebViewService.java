package com.libo.common.autoservice;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

/**
 * /**
 * 接口下沉
 * @Author libo
 * @create 2021/5/28 9:56 上午
 * @describe:
 */
public  interface IWebViewService {
    void startWebViewActivity(Context context,String url,String title,boolean isShowActionBar);
    Fragment getWebViewFragment(String url, boolean canNativeRefresh);
    void  startDemoHtml(Context context);

}
