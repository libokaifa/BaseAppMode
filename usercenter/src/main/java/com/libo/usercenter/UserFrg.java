package com.libo.usercenter;

import com.libo.base.mvvm.view.BaseMvvmFragment;
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

    }
}
