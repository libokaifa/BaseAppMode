package com.libo.base.widgets.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.libo.base.R;


/**
 * Created by xiaoshu on 2019-11-26.
 * yangshuling@dobest.com
 */
public class CustomProgressDialog extends BaseDialog {

    private TextView tvHint;
    private LinearLayout layoutRoot;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_custom_progress;
    }

    @Override
    public void onViewCreate() {
        tvHint = mRootView.findViewById(R.id.tv_hint);
        layoutRoot = mRootView.findViewById(R.id.layout_root);
    }

    public void setHintText(String hintText) {
//        layoutRoot.post(() -> setWidthHeightWithRatio(layoutRoot, 1, 1));
        if (TextUtils.isEmpty(hintText)) {
            tvHint.setVisibility(View.GONE);
        } else {
            tvHint.setText(hintText);
            tvHint.setVisibility(View.VISIBLE);
        }
    }

    //根据宽高比设置控件宽高, 如设置宽高比为5:4，那么widthRatio为5，heightRatio为4
    public static void setWidthHeightWithRatio(View view, int widthRatio, int heightRatio) {
        int width = view.getWidth();
        int height = width * heightRatio / widthRatio;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = height;
            layoutParams.width = width;
            view.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void show() {
        super.show();
        Window window = mDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.width =WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(windowParams);
        }
    }
}
