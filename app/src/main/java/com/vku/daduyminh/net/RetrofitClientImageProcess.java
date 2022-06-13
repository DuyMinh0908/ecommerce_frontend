package com.vku.daduyminh.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientImageProcess{


    private static RetrofitClientImageProcess mInstance;
    private final Retrofit retrofit;


    private RetrofitClientImageProcess(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClientImageProcess getInstance(String URL) {
        if (mInstance == null) {
            mInstance = new RetrofitClientImageProcess(URL);
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }

}
