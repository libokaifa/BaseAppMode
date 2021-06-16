package com.libo.network.base;

import android.app.Application;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/31 9:49 上午
 * @describe: 保存基础参数
 */
public interface INetworkRequiredInfo {
    String getAppVersionCode();
    boolean isDebug();
    Application getApplicationContext();
}
