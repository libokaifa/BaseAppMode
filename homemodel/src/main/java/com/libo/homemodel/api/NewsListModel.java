package com.libo.homemodel.api;

import com.libo.base.customview.BaseCustomViewModel;
import com.libo.base.mvvm.model.BaseMvvmModel;
import com.libo.base.util.LiboLoger;
import com.libo.common.picturetitleview.PictureTitleViewModel;
import com.libo.common.titleview.TitleViewModel;
import com.libo.network.LiBoNetWorkApi;
import com.libo.network.observer.BaseObserver;
import com.libo.homemodel.api.NewsApiInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * /**
 *
 * @Author libo
 * @create 2021/6/15 3:58 下午
 * @describe:
 */
public  class NewsListModel extends BaseMvvmModel<ListBean, List<ListBean.Datas>> {

    public NewsListModel() {
        super(true, null, null,0);

    }

    @Override
    public void load() {
        LiBoNetWorkApi.getService(NewsApiInterface.class)
                .getNewsList()
                .compose(LiBoNetWorkApi.getInstance().applySchedulers(new BaseObserver<ListBean>(this,this)));
        LiboLoger.log("3===<><>");
    }


    @Override
    public void onSuccess(ListBean t, boolean isFromCache) {
        LiboLoger.log("2===<><>");
        notifyResultToListener(t, t.datas, isFromCache);
    }

    @Override
    public void onFailure(Throwable e) {
        loadFail(e.getMessage());
    }
}
