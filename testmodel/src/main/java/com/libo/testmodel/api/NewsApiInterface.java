package com.libo.testmodel.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * /**
 *
 * @Author libo
 * @create 2021/6/7 4:18 下午
 * @describe:
 */
public  interface NewsApiInterface {

    @GET("release/news")
    default Observable<NewsListBean> getNewsList(@Query("channelId") String channelId,
                                                 @Query("channelName") String channelName,
                                                 @Query("page") String page) {
        return null;
    }

    @GET("release/channel")
    Observable<NewsChannelsBean> getNewsChannels();

}
