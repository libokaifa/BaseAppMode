package com.libo.network;

import com.libo.network.base.NetWorkApi;
import com.libo.network.bean.BaseResponse;
import com.libo.network.errorhand.ExceptionHandle;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/31 1:24 下午
 * @describe:
 */
public class LiBoNetWorkApi extends NetWorkApi {
    private  static  volatile  LiBoNetWorkApi mInstance;
    public  static  LiBoNetWorkApi getInstance(){
        if (mInstance==null){
            synchronized (LiBoNetWorkApi.class){
                if (mInstance==null){
                    mInstance=new LiBoNetWorkApi();
                }
            }
        }
        return mInstance;
    }
    public static  <T> T getService(Class<T> service) {
        return getInstance().getRetrofit(service).create(service);
    }


    /**
     *
     * @return
     */
    @Override
    protected Interceptor getInterceptor() {
        return null;
    }


    protected <T> Function<T, T> getAppErrorHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(T response) throws Exception {
                //response中code码不会0 出现错误
                if (response instanceof BaseResponse && ((BaseResponse) response).showapiResCode != 0) {
                    ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
                    exception.code = ((BaseResponse) response).showapiResCode;
                    exception.message = ((BaseResponse) response).showapiResError != null ? ((BaseResponse) response).showapiResError : "";
                    throw exception;
                }
                return response;
            }
        };
    }

    @Override
    public String getRealse() {
        return "http:/fff/";
    }

    @Override
    public String getTest() {
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/";
    }
}
