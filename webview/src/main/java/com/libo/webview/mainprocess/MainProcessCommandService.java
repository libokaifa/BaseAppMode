package com.libo.webview.mainprocess;

import android.app.Service;
import android.content.Intent;
import android.os.Debug;
import android.os.IBinder;

import com.libo.common.logger.LiboLoger;

import androidx.annotation.Nullable;

public class MainProcessCommandService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LiboLoger.log("jjjj");
        return MainProcessCommandsManager.getInstance();
    }
}
