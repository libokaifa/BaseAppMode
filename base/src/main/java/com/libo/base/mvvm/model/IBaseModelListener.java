package com.libo.base.mvvm.model;

public interface IBaseModelListener<DATA> {
    void onLoadSuccess( DATA data, PagingResult result);
    void onLoadFail( String message, PagingResult result);
}
