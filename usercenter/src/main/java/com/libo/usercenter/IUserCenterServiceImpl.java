package com.libo.usercenter;

import android.content.Intent;

import com.google.auto.service.AutoService;
import com.libo.base.baseapp.BaseApplication;
import com.libo.common.autoservice.IUserCenterService;


@AutoService({IUserCenterService.class})
public class IUserCenterServiceImpl implements IUserCenterService {
    @Override
    public boolean isLogined() {
        return false;
    }

    @Override
    public void login() {
        Intent intent = new Intent(BaseApplication.mApplication,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.mApplication.startActivity(intent);
    }
}
