package com.libo.network.bean;



/**
 * /**
 *
 * @Author libo
 * @create 2021/5/31 4:44 下午
 * @describe:
 */
public  class BaseResponse<Data> {
    public int errorCode;
    public String errorMsg;
    public Data data;
}
