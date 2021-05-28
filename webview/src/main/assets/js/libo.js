var libo = {};
libo.os = {};
libo.os.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
libo.os.isAndroid = !libo.os.isIOS;
libo.callbacks = {}

libo.callback = function (callbackname, response) {
   var callbackobject = libo.callbacks[callbackname];
   console.log("xxxx"+callbackname);
   if (callbackobject !== undefined){
       if(callbackobject.callback != undefined){
          console.log("xxxxxx"+response);
            var ret = callbackobject.callback(response);
           if(ret === false){
               return
           }
           delete libo.callbacks[callbackname];
       }
   }
}

libo.takeNativeAction = function(commandname, parameters){
    console.log("libo takenativeaction")
    var request = {};
    request.name = commandname;
    request.param = parameters;
    if(window.libo.os.isAndroid){
        console.log("android take native action" + JSON.stringify(request));
        window.libowebview.takeNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.libowebview.postMessage(JSON.stringify(request))
    }
}

libo.takeNativeActionWithCallback = function(commandname, parameters, callback) {
    var callbackname = "nativetojs_callback_" +  (new Date()).getTime() + "_" + Math.floor(Math.random() * 10000);
    libo.callbacks[callbackname] = {callback:callback};

    var request = {};
    request.name = commandname;
    request.param = parameters;
    request.param.callbackname = callbackname;
    if(window.libo.os.isAndroid){
        window.libowebview.takeNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.libowebview.postMessage(JSON.stringify(request))
    }
}

window.libo = libo;
