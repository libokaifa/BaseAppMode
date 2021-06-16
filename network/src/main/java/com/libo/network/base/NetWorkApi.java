package com.libo.network.base;

import com.libo.network.commoninterceptor.CommonRequestInterceptor;
import com.libo.network.commoninterceptor.CommonResponseInterceptor;
import com.libo.network.errorhand.HttpErrorHandler;
import com.libo.network.netpath.NetPathActivity;
import com.libo.network.netpath.NetPathCallBack;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * /**
 *
 * @Author libo
 * @create 2021/5/30 4:12 下午
 * @describe:
 */
public abstract class NetWorkApi implements NetPathCallBack {
    public static HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();
    private String mBaseUrl;
    private OkHttpClient mOkHttpClient;
    private static boolean mRealse = true;
    private static INetworkRequiredInfo miNetworkRequiredInfo;

    public NetWorkApi() {
        if (!mRealse) {
            mBaseUrl = getTest();
        }
        mBaseUrl = getRealse();
    }

    public static void init(INetworkRequiredInfo networkRequiredInfo) {
        miNetworkRequiredInfo = networkRequiredInfo;
        mRealse = NetPathActivity.isOfficialEnvironment(networkRequiredInfo.getApplicationContext());
    }

    protected Retrofit getRetrofit(Class service) {
        if (retrofitHashMap.get(mBaseUrl + service.getName()) != null) {
            return retrofitHashMap.get(mBaseUrl + service.getName());
        }
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(mBaseUrl);
        retrofitBuilder.client(getOkHttpClient());
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = retrofitBuilder.build();
        retrofitHashMap.put(mBaseUrl + service.getName(), retrofit);
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            if (getInterceptor() != null) {
                okHttpClientBuilder.addInterceptor(getInterceptor());
            }
            int cacheSize = 100 * 1024 * 1024; // 10MB
            okHttpClientBuilder.cache(new Cache(miNetworkRequiredInfo.getApplicationContext().getCacheDir(), cacheSize));
            okHttpClientBuilder.addInterceptor(new CommonRequestInterceptor(miNetworkRequiredInfo));
            okHttpClientBuilder.addInterceptor(new CommonResponseInterceptor(miNetworkRequiredInfo));
            mOkHttpClient = okHttpClientBuilder.build();
        }
        return mOkHttpClient;
    }

    public <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = (Observable<T>)upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(getAppErrorHandler())
                        .onErrorResumeNext(new HttpErrorHandler<T>());
                observable.subscribe(observer);
                return observable;
            }
        };
    }



    protected abstract Interceptor getInterceptor();

    protected abstract <T> Function<T, T> getAppErrorHandler();
}
