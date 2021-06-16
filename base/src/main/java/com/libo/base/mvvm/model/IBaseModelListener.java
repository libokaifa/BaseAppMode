package com.libo.base.mvvm.model;

public interface IBaseModelListener<DATA> {
    void onLoadSuccess(BaseMvvmModel model, DATA data, PagingResult... result);
    void onLoadFail(BaseMvvmModel model, String message, PagingResult... result);
}
