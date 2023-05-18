package com.libo.base.mvvm.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
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
public abstract class BaseDataBingBottomDialogFragment<V extends ViewDataBinding> extends DialogFragment implements CustomAdapt {
    protected V viewDataBinding;

    public abstract @LayoutRes
    int getLayoutId();



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
        lp.gravity=Gravity.BOTTOM;
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.DialogFragmentStyle);
        onViewCreated(savedInstanceState);


    }
   protected  void  setCanFalse(){
       getDialog().setCanceledOnTouchOutside(false);
       setCancelable(false);
       getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
           @Override
           public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
               if (keyCode == KeyEvent.KEYCODE_BACK) {
                   return true;
               }
               return false;
           }
       });
   }




    /**
     *创建完成
     * @param savedInstanceState
     */
    protected  abstract void onViewCreated(Bundle savedInstanceState);
    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 375;
    }
}
