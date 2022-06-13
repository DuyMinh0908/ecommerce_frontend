package com.vku.daduyminh.net;

import static com.vku.daduyminh.utils.Constant.LOCALHOST;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static RetrofitClient mInstance;
    private final Retrofit retrofit;


    private RetrofitClient(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(String URL) {
        if (mInstance == null) {
            mInstance = new RetrofitClient(URL);
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }

}
