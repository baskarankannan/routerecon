package com.techacsent.route_recon.retrofit_api;

import android.util.Log;

import com.techacsent.route_recon.BuildConfig;
import com.techacsent.route_recon.utills.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;



    public static Retrofit getClient() {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request request = chain.request();
                        Response response = chain.proceed(request);

                        if( response.code() == 500){

                            Log.e("ServerError","500" );

                            //Toast.makeText(Application.getInstance(), "Error", Toast.LENGTH_SHORT).show();

                            return response;

                        }


                        return response;
                    }
                })
                .connectTimeout(140, TimeUnit.SECONDS)
                .readTimeout(140, TimeUnit.SECONDS)
                .writeTimeout(140, TimeUnit.SECONDS)
                .hostnameVerifier(new CustomHostnameVerifier())
                .build();




        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }
}
