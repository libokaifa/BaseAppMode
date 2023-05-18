package com.libo.homemodel.api;

import com.libo.network.bean.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * /**
 *
 * @Author libo
 * @create 2021/6/7 4:18 下午
 * @describe:
 */
public  interface NewsApiInterface {

    @GET("article/list/0/json")
     Observable<BaseResponse<ListBean>> getNewsList();



}
