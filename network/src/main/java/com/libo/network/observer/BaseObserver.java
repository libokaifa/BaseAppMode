package com.libo.network.observer;



import androidx.annotation.NonNull;

import com.libo.base.mvvm.model.BaseMvvmModel;
import com.libo.base.mvvm.model.MvvmDataObserver;
import com.libo.network.bean.BaseResponse;
import com.libo.network.errorhand.ExceptionHandle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BaseObserver<T> implements Observer<BaseResponse<T>> {
    BaseMvvmModel baseModel;
    MvvmDataObserver<T> mvvmDataObserver;
    public BaseObserver(BaseMvvmModel baseModel, MvvmDataObserver<T> mvvmDataObserver) {
        this.baseModel = baseModel;
        this.mvvmDataObserver = mvvmDataObserver;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if(baseModel != null){
            baseModel.addDisposable(d);
        }
    }

    @Override
    public void onNext(@NonNull BaseResponse<T> tBaseResponse) {
        mvvmDataObserver.onSuccess(tBaseResponse.data, false);
    }




    @Override
    public void onError(Throwable e) {
        if(e instanceof ExceptionHandle.ResponeThrowable){
            mvvmDataObserver.onFailure(e);
        } else {
            mvvmDataObserver.onFailure(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {

    }
}
