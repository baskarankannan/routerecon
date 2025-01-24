package com.techacsent.route_recon.retrofit_api;

import android.app.Activity;

import java.util.HashMap;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ApiOkhttpCaller {

    public static void okHttpGet(String getUrl,/* HashMap<String, Integer> queryParams, HashMap<String, String> headers,*/ Activity activity, RouteHttpCallback callback) {
        Request.Builder requestBuilder = new Request.Builder();
        /*if (queryParams != null) {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(getUrl).newBuilder();
            //addQueryParams(queryParams, urlBuilder);
            requestBuilder.url(urlBuilder.build());
        } else {
            requestBuilder.url(getUrl);
        }*/

        requestBuilder.url(getUrl);
        //addHeaders(headers, requestBuilder, activity);
        final Request request = requestBuilder.get().build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);

    }
}
