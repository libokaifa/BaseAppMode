package com.libo.baseappmodel;

import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.NotificationUtils;
import com.blankj.utilcode.util.Utils;
import com.libo.base.mvvm.view.BaseMvvmActivity;
import com.libo.baseappmodel.databinding.ActivityMainBinding;

public class  MainActivity extends BaseMvvmActivity<ActivityMainBinding,MainActivityVm> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int initViewModeId() {
        return BR.vm;
    }

    @Override
    public void onViewCreate() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
        NavController controller = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(viewDataBinding.navView,controller);
        BarUtils.transparentStatusBar(this);
        BarUtils.setStatusBarLightMode(this,true);


    }

    @Override
    public void initLiveDataLister() {
        super.initLiveDataLister();

    }
}