package com.techacsent.route_recon.retrofit_api;

import android.app.Activity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RouteHttpCallback implements Callback, Runnable {

    private Activity activity;
    private RouteResponse mResponse;
    private Call call;
    private IOException exception;
    private String networkResponse;
    private int responseCode;
    private String responseString;
    private String body;


    public RouteHttpCallback(Activity activity, RouteResponse mResponse) {
        this.activity = activity;
        this.mResponse = mResponse;
    }

    @Override
    public void run() {
        if (exception != null) {
            mResponse.onFailure(call, exception);
        } else {
            try {
                mResponse.onResponse(call, networkResponse, responseCode, responseString, body);
            } catch (IOException e) {
                mResponse.onFailure(call, e);
            }catch (Exception e) {
                //mResponse.onFailure(call, e);
            }
        }

    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        this.call = call;
        this.exception = e;
        activity.runOnUiThread(this);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        this.call = call;
        this.networkResponse = Objects.requireNonNull(response.networkResponse()).toString();
        this.responseCode = response.code();
        this.responseString = response.toString();
        this.body = Objects.requireNonNull(response.body()).string();
        try {
            this.activity.runOnUiThread(this);
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    public interface RouteResponse {
        void onFailure(Call call, IOException e);

        void onResponse(Call call, String networkResponse, int responseCode, String responseString, String body) throws IOException;
    }
}
