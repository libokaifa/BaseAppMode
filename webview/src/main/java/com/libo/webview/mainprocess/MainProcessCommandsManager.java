package com.libo.webview.mainprocess;

import android.os.RemoteException;

import com.alibaba.fastjson.JSON;
import com.libo.webview.ICallbackFromMainprocessToWebViewProcessInterface;
import com.libo.webview.IWebviewProcessToMainProcessInterface;
import com.libo.webview.command.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/28 1:56 下午
 * @describe:
 */
public  class MainProcessCommandsManager extends IWebviewProcessToMainProcessInterface.Stub {
    private static MainProcessCommandsManager sInstance;

    /**
     * 命令模式的集合
     */
    private static HashMap<String, Command> mCommands = new HashMap<>();
    public static MainProcessCommandsManager getInstance() {
        if (sInstance == null) {
            synchronized (MainProcessCommandsManager.class) {
                sInstance = new MainProcessCommandsManager();
            }
        }
        return sInstance;
    }

    private MainProcessCommandsManager(){
        ServiceLoader<Command> serviceLoader=ServiceLoader.load(Command.class);
        for (Command command: serviceLoader){
             if (!mCommands.containsKey(command.name())){
                 mCommands.put(command.name(),command);
             }
        }
    }
    public void executeCommand(String commandName, Map params, ICallbackFromMainprocessToWebViewProcessInterface callback) {
        mCommands.get(commandName).execute(params, callback);
    }
    @Override
    public void handleWebCommand(String commandName, String jsonParams, ICallbackFromMainprocessToWebViewProcessInterface callback) throws RemoteException {
        MainProcessCommandsManager.getInstance().executeCommand(commandName, JSON.parseObject(jsonParams,Map.class), callback);
    }
}
