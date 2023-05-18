package com.libo.base.mvvm.view;


import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;


import com.libo.base.R;

import org.jetbrains.annotations.NotNull;

import me.jessyan.autosize.internal.CustomAdapt;

/**
 * @Description TODO
 * @Author libo
 * @Date 2021/9/15 13:59
 */
public abstract class BaseDataBingDialogFragment<V extends ViewDataBinding> extends DialogFragment implements CustomAdapt {
    protected V viewDataBinding;

    public abstract @LayoutRes
    int getLayoutId();

    // 避免个别机型的反射空构造
    public BaseDataBingDialogFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.DialogFragmentStyle);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.CENTER);
        //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 0, 0);
        //消除边距
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity=Gravity.CENTER;
        window.setAttributes(lp);
        onViewCreated(savedInstanceState);
        window.setWindowAnimations(R.style.DialogFragmentStyle);
        onViewCreated();

    }


    /**
     *创建完成
     */
    protected abstract void onViewCreated();


    /**
     *创建完成
     * @param savedInstanceState
     */
    protected  void onViewCreated(Bundle savedInstanceState){

    }
    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 375;
    }
}
