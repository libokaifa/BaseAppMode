package com.libo.baseappmodel.webviewdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;

import com.google.auto.service.AutoService;
import com.libo.base.baseapp.BaseApplication;
import com.libo.webview.ICallbackFromMainprocessToWebViewProcessInterface;
import com.libo.webview.command.Command;
;

import java.util.Map;

@AutoService({Command.class})
public class CommandOpenPage implements Command {

    @Override
    public String name() {
        return "openPage";
    }

    @Override
    public void execute(Map parameters, ICallbackFromMainprocessToWebViewProcessInterface callback) {
        String targetClass = String.valueOf(parameters.get("target_class"));
        if (!TextUtils.isEmpty(targetClass)) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(BaseApplication.mApplication, targetClass));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseApplication.mApplication.startActivity(intent);
        }
    }
}
