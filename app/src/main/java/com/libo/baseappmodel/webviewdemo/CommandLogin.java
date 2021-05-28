package com.libo.baseappmodel.webviewdemo;

import android.os.RemoteException;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.auto.service.AutoService;

import com.libo.base.autoservice.LiboServiceLoader;
import com.libo.common.autoservice.IUserCenterService;
import com.libo.common.eventbus.LoginEvent;
import com.libo.common.logger.LiboLoger;
import com.libo.webview.ICallbackFromMainprocessToWebViewProcessInterface;
import com.libo.webview.command.Command;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

@AutoService({Command.class})
public class CommandLogin implements Command {
    IUserCenterService iUserCenterService = LiboServiceLoader.load(IUserCenterService.class);
    ICallbackFromMainprocessToWebViewProcessInterface callback;
    String callbacknameFromNativeJs;
    public CommandLogin(){
        EventBus.getDefault().register(this);
    }

    @Override
    public String name() {
        return "login";
    }

    @Override
    public void execute(final Map parameters, ICallbackFromMainprocessToWebViewProcessInterface callback) {
        LiboLoger.log("CommandLogin::"+JSON.toJSONString(parameters));
        if (iUserCenterService != null && !iUserCenterService.isLogined()) {
            iUserCenterService.login();
            this.callback = callback;
            this.callbacknameFromNativeJs = parameters.get("callbackname").toString();
        }
    }

    @Subscribe
    public void onMessageEvent(LoginEvent event) {
        Log.d("CommandLogin", event.userName);
        HashMap map = new HashMap();
        map.put("accountName", event.userName);
        if(this.callback != null) {
            try {
                this.callback.onResult(callbacknameFromNativeJs, JSON.toJSONString(map));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
