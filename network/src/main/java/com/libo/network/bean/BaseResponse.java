package com.libo.network.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/31 4:44 下午
 * @describe:
 */
public  class BaseResponse {
    @SerializedName("result_code")
    @Expose
    public Integer showapiResCode;
    @SerializedName("result_error_msg")
    @Expose
    public String showapiResError;
}
