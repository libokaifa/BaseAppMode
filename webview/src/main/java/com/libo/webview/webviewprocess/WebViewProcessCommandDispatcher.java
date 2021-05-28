package com.libo.webview.webviewprocess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.libo.base.baseapp.BaseApplication;
import com.libo.webview.ICallbackFromMainprocessToWebViewProcessInterface;
import com.libo.webview.IWebviewProcessToMainProcessInterface;
import com.libo.webview.mainprocess.MainProcessCommandService;

/**
 * /**
 * 绑定启动服务
 * @Author libo
 * @create 2021/5/28 1:33 下午
 * @describe:
 */
public  class WebViewProcessCommandDispatcher  implements ServiceConnection {
    private static  WebViewProcessCommandDispatcher mInstance;
    private IWebviewProcessToMainProcessInterface iWebviewProcessToMainProcessInterface;

    public  static  WebViewProcessCommandDispatcher getInstance(){
        if (mInstance==null){
            synchronized (WebViewProcessCommandDispatcher.class){
                mInstance=new WebViewProcessCommandDispatcher();
            }
        }
        return  mInstance;
    }


    public void initAidlConnection(){
        Intent intent = new Intent(BaseApplication.mApplication, MainProcessCommandService.class);
        BaseApplication.mApplication.bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        iWebviewProcessToMainProcessInterface=IWebviewProcessToMainProcessInterface.Stub.asInterface(service);

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        iWebviewProcessToMainProcessInterface = null;
        initAidlConnection();
    }
    @Override
    public void onBindingDied(ComponentName name) {
        iWebviewProcessToMainProcessInterface = null;
        initAidlConnection();
    }


    public  void  executeCommand(String commandName, String params, final BaseWebView baseWebView){
        if (iWebviewProcessToMainProcessInterface!=null){
            try {
                iWebviewProcessToMainProcessInterface.handleWebCommand(commandName, params, new ICallbackFromMainprocessToWebViewProcessInterface.Stub() {
                    @Override
                    public void onResult(String callbackname, String response) throws RemoteException {
                        baseWebView.handleCallback(callbackname,response);
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
