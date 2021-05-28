package com.libo.baseappmodel.webviewdemo;

import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.auto.service.AutoService;
import com.libo.base.baseapp.BaseApplication;
import com.libo.common.logger.LiboLoger;
import com.libo.webview.ICallbackFromMainprocessToWebViewProcessInterface;
import com.libo.webview.command.Command;
import com.orhanobut.logger.Logger;


import java.util.Map;

@AutoService({Command.class})
public class CommandShowToast implements Command {
    @Override
    public String name() {
        return "showToast";
    }

    @Override
    public void execute(final Map parameters, ICallbackFromMainprocessToWebViewProcessInterface callback) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Logger.e(parameters.get("message")+"");
                Toast.makeText(BaseApplication.mApplication, String.valueOf(parameters.get("message")), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
