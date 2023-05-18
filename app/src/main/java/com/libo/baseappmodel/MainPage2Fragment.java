package com.libo.baseappmodel;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.libo.base.autoservice.LiboServiceLoader;
import com.libo.base.mvvm.view.BaseMvvmFragment;

import com.libo.baseappmodel.databinding.FragmentMainPage2Binding;
import com.libo.common.autoservice.IWebViewService;


public class MainPage2Fragment
        extends BaseMvvmFragment<FragmentMainPage2Binding,MainPage2FragmentVm> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_page2;
    }

    @Override
    public int initViewModeId() {
        return BR.vm;
    }

    @Override
    protected void onViewCreated() {
        viewDataBinding.tvWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IWebViewService webViewService= LiboServiceLoader.load(IWebViewService.class);
                if (webViewService!=null){
                    webViewService.startDemoHtml(getContext());
                }
            }
        });
    }
}
