package com.libo.base.mvvm.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.blankj.utilcode.util.BarUtils;
import com.libo.base.R;


import me.jessyan.autosize.internal.CustomAdapt;

/**
 * author : LiBo
 * date : 2023/3/6 17:50
 * description :
 */
public abstract class BaseDataBingDialogActivity <V extends ViewDataBinding> extends Activity implements CustomAdapt {

    protected V viewDataBinding;

    public abstract @LayoutRes
    int getLayoutId();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 0, 0);
        //消除边距
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity=Gravity.TOP;
        window.setAttributes(lp);





        viewDataBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), null, false);
        setContentView(viewDataBinding.getRoot());
        onViewCreated(savedInstanceState);
        viewDataBinding.getRoot().setBackgroundColor(getResources().getColor(R.color.color_black));
        viewDataBinding.getRoot().getBackground().setAlpha(125);
        BarUtils.transparentStatusBar(this);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    protected abstract void onViewCreated(Bundle savedInstanceState);

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 375;
    }

    @Override
    public void onBackPressed() {

    }
}
