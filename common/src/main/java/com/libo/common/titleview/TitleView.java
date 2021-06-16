package com.libo.common.titleview;

import android.content.Context;
import android.view.View;

import com.libo.base.autoservice.LiboServiceLoader;
import com.libo.base.customview.BaseCustomView;
import com.libo.common.R;
import com.libo.common.autoservice.IWebViewService;
import com.libo.common.databinding.TitleViewBinding;


public class TitleView extends BaseCustomView<TitleViewBinding, TitleViewModel> {
    public TitleView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.title_view;
    }

    @Override
    public void onRootClicked(View view) {
        IWebViewService iWebViewService= LiboServiceLoader.load(IWebViewService.class);
        iWebViewService.startWebViewActivity(getContext(),"News",data.jumpUri,true);
    }

    @Override
    protected void setDataToView(TitleViewModel titleViewModel) {
        binding.setViewModel(titleViewModel);
    }
}
