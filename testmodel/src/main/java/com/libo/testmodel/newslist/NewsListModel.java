package com.libo.testmodel.newslist;

import com.libo.base.customview.BaseCustomViewModel;
import com.libo.base.mvvm.model.BaseMvvmModel;
import com.libo.common.picturetitleview.PictureTitleViewModel;
import com.libo.common.titleview.TitleViewModel;
import com.libo.network.LiBoNetWorkApi;
import com.libo.network.observer.BaseObserver;
import com.libo.testmodel.api.NewsApiInterface;
import com.libo.testmodel.api.NewsListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * /**
 *
 * @Author libo
 * @create 2021/6/15 3:58 下午
 * @describe:
 */
public  class NewsListModel extends BaseMvvmModel<NewsListBean, List<BaseCustomViewModel>> {
    private String mChannelId;
    private String mChannelName;
    public NewsListModel(String channelId, String channelName) {
        super(true, channelId + channelName + "_preference_key", null,1);
        mChannelId = channelId;
        mChannelName = channelName;
    }

    @Override
    public void load() {
        LiBoNetWorkApi.getService(NewsApiInterface.class)
                .getNewsList(mChannelId,mChannelName,String.valueOf(mPage))
                .compose(LiBoNetWorkApi.getInstance().applySchedulers(new BaseObserver<NewsListBean>(this,this)));
    }

    @Override
    public void onSuccess(NewsListBean newsListBean, boolean isFromCache) {
        List<BaseCustomViewModel> viewModels = new ArrayList<>();
        for (NewsListBean.Contentlist contentlist : newsListBean.showapiResBody.pagebean.contentlist) {
            if (contentlist.imageurls != null && contentlist.imageurls.size() > 0) {
                PictureTitleViewModel pictureTitleViewModel = new PictureTitleViewModel();
                pictureTitleViewModel.pictureUrl = contentlist.imageurls.get(0).url;
                pictureTitleViewModel.jumpUri = contentlist.link;
                pictureTitleViewModel.title = contentlist.title;
                viewModels.add(pictureTitleViewModel);
            } else {
                TitleViewModel titleViewModel = new TitleViewModel();
                titleViewModel.jumpUri = contentlist.link;
                titleViewModel.title = contentlist.title;
                viewModels.add(titleViewModel);
            }
        }
        notifyResultToListener(newsListBean, viewModels, isFromCache);
    }

    @Override
    public void onFailure(Throwable e) {
        loadFail(e.getMessage());
    }
}
