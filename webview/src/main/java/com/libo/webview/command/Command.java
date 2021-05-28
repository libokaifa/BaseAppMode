package com.libo.webview.command;



import com.libo.webview.ICallbackFromMainprocessToWebViewProcessInterface;

import java.util.Map;

public interface Command {
    String name();
    void execute(Map parameters, ICallbackFromMainprocessToWebViewProcessInterface callback);
}
