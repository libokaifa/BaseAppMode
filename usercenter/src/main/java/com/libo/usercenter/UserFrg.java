package com.libo.usercenter;

import android.content.Intent;
import android.view.View;

import com.libo.base.mvvm.view.BaseMvvmFragment;
import com.libo.network.netpath.NetPathActivity;
import com.libo.usercenter.databinding.UserFrgBinding;

/**
 * author : LiBo
 * date : 2023/5/16 14:16
 * description :
 */
public class UserFrg extends BaseMvvmFragment<UserFrgBinding,UserFrgVm> {
    @Override
    public int getLayoutId() {
        return R.layout.user_frg;
    }

    @Override
    public int initViewModeId() {
        return BR.vm;
    }

    @Override
    protected void onViewCreated() {
        viewDataBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(),NetPathActivity.class));
            }
        });
    }
}
