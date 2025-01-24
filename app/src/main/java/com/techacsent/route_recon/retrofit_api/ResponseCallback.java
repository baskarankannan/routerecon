package com.techacsent.route_recon.retrofit_api;

public interface ResponseCallback<T> {
    void onSuccess(T data);

    void onError(Throwable th);
}
