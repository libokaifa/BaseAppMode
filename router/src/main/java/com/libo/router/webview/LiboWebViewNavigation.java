package com.libo.router.webview;

import android.app.Activity;

import com.alibaba.android.arouter.launcher.ARouter;


/**
 * author : LiBo
 * date : 2022/8/25 10:22
 * description :
 */
public class LiboWebViewNavigation {
    private static LiboWebViewNavigation sNavigation = new LiboWebViewNavigation();

    private LiboWebViewNavigation() {
    }

    public static LiboWebViewNavigation getInstance() {
        return sNavigation;
    }



    public  void  actionStart(Activity context,String title,boolean isShowActionBar,String url){
        ARouter.getInstance()
                .build(WebViewPath.Web_ACTIVITY)
                .withString("title",title)
                .withBoolean("isShowActionBar",isShowActionBar)
                .withString("url",url)
                .navigation(context);
    }
}
