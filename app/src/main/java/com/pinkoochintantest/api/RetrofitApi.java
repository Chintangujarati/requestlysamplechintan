package com.pinkoochintantest.api;

import android.content.Context;

import io.requestly.rqinterceptor.api.RQCollector;
import io.requestly.rqinterceptor.api.RQInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitApi {

    private static RetrofitApi instance = null;
    private ApiInterface myApi;

    private RetrofitApi(Context context) {

        RQCollector rqCollector = new RQCollector(context, "");
        RQInterceptor.Builder rqInterceptor = new RQInterceptor.Builder(context);
        rqInterceptor.collector(rqCollector);
        RQInterceptor interceptor = rqInterceptor.build();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        myApi = retrofit.create(ApiInterface.class);
    }

    public static synchronized RetrofitApi getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitApi(context);
        }
        return instance;
    }

    public ApiInterface getMyApi() {
        return myApi;
    }
}
