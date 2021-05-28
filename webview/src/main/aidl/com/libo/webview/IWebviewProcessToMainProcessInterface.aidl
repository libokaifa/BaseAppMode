// IWebviewProcessToMainProcessInterface.aidl
package com.libo.webview;
import  com.libo.webview.ICallbackFromMainprocessToWebViewProcessInterface;
// Declare any non-default types here with import statements

interface IWebviewProcessToMainProcessInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void handleWebCommand(String commandName,String jsonParams,in ICallbackFromMainprocessToWebViewProcessInterface callback);
}