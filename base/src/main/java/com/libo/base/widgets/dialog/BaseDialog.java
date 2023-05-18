package com.libo.base.widgets.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.libo.base.R;



public abstract class BaseDialog {

    protected AlertDialog mDialog;
    protected View mRootView;
    protected Context mContext;
    protected ButtonState mButtonState = ButtonState.BOTH;

    public BaseDialog(Context context) {
        this.mContext = context;
        init();
    }

    protected void init() {
        mRootView = LayoutInflater.from(mContext).inflate(getLayoutId(), null, false);
        onViewCreate();
        mDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogStyle)
                .setView(mRootView)
                .create();
    }
    public void showWithWidthMargin(int marginValue){
        Activity activity = ActivityUtils.getActivityByContext(mContext);
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (mDialog != null && !mDialog.isShowing()) {
            try {
                mDialog.show();
            } catch (Exception pE) {
                pE.printStackTrace();
                return;
            }
        }
        Window window = mDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.width = ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(marginValue * 2);
            window.setAttributes(windowParams);
        }
        onDialogShow();
    }

    public void show() {
        showWithWidthMargin(33);
    }

    public boolean isShowing() {
        return mDialog != null && mDialog.isShowing();
    }

    public void dismiss() {

        if (mDialog == null) {
            return;
        }
        Activity activity = ActivityUtils.getActivityByContext(mContext);
        if (activity == null || activity.isFinishing()) {
            return;
        }
        try {
            mDialog.dismiss();
        } catch (Exception pE) {
            pE.printStackTrace();
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        if (mDialog != null) {
            mDialog.setOnDismissListener(listener);
        }

    }

    public void setCancelable(boolean cancelable) {
        if (mDialog != null) {
            mDialog.setCancelable(cancelable);
        }
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        if (mDialog != null) {
            mDialog.setCanceledOnTouchOutside(cancel);
        }
    }

    public abstract int getLayoutId();

    public abstract void onViewCreate();

    public void onDialogShow() {}
    public enum ButtonState{
        CONFIRM,CANCEL,BOTH
    }
}
