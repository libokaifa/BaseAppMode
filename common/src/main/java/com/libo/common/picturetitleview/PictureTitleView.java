package com.libo.common.picturetitleview;

import android.content.Context;
import android.view.View;

import com.libo.base.autoservice.LiboServiceLoader;
import com.libo.base.customview.BaseCustomView;
import com.libo.common.R;
import com.libo.common.autoservice.IWebViewService;
import com.libo.common.databinding.PictureTitleViewBinding;


public class PictureTitleView extends BaseCustomView<PictureTitleViewBinding, PictureTitleViewModel> {
    public PictureTitleView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.picture_title_view;
    }

    @Override
    public void onRootClicked(View view) {
        IWebViewService iWebViewService= LiboServiceLoader.load(IWebViewService.class);
        iWebViewService.startWebViewActivity(getContext(),"News",data.jumpUri,true);

    }

    @Override
    protected void setDataToView(PictureTitleViewModel pictureTitleViewModel) {
        binding.setViewModel(pictureTitleViewModel);
    }
}
