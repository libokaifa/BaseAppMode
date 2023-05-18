package com.libo.base.mvvm.viewmodel;
/**
 * @Description TODO
 * @Author libo
 * @Date 2021/9/6 10:55
 */
public enum ViewStatus {
    LOADING,//第一次loading
    LIST_lLOADING,//下拉刷新loading
    LIST_SHOW_CONTENT,
    EMPTY,
    SHOW_CONTENT,
    NO_MORE_DATA,
    REFRESH_ERROR,
    LOAD_MORE_FAILED,
    NETERR
}
