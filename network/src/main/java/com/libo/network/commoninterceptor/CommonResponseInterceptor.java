package com.libo.network.commoninterceptor;

import android.app.Activity;
import android.util.ArrayMap;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.libo.base.util.LiboLoger;
import com.libo.network.base.INetworkRequiredInfo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

import static com.alibaba.fastjson.util.IOUtils.UTF8;
import static com.alibaba.fastjson.util.IOUtils.stringSize;

public class CommonResponseInterceptor implements Interceptor {
    private static final String TAG = "ResponseInterceptor";
    INetworkRequiredInfo iNetworkRequiredInfo;

    public CommonResponseInterceptor(INetworkRequiredInfo iNetworkRequiredInfo) {
        this.iNetworkRequiredInfo = iNetworkRequiredInfo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        String body = null;
        try {
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                body = buffer.readString(charset);
            }
        } catch (Exception e) {
            LiboLoger.log(request.url()+"  err");
        }
        long requestTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        ResponseBody responseBody=response.body();
        String rBody = null;
        if (HttpHeaders.hasBody(response)) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    LiboLoger.log("Erro_Response_URL:" + request.url());
                    e.printStackTrace();
                }
            }
            rBody = buffer.clone().readString(charset);
        }
        handerLog(request.url().toString(),request.method(),request.headers().toString(),body,String.valueOf(response.code()),response.message(),rBody,String.valueOf(System.currentTimeMillis() - requestTime));
        return response;
    }

    private void handerLog(String url,String method,String headers,String requestBody,String responseCode,String responseMessage,String responseBody,String requestTime) {
        if (iNetworkRequiredInfo.isDebug()){
            Map<String,Object> map =new ArrayMap<>();
            map.put("url",url);
            map.put("method",method);
            map.put("headers",headers);
            map.put("request body",requestBody);

            map.put("responseCode",responseCode);
            map.put("message",responseMessage);
            map.put("response body",requestBody);
            map.put("take time",requestTime);
            LiboLoger.logJson(JSON.toJSONString(map));
            LiboLoger.logJson(responseBody);
        }



    }
}
