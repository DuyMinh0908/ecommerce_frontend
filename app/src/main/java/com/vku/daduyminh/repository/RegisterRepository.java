package com.vku.daduyminh.repository;

import static com.vku.daduyminh.utils.Constant.LOCALHOST;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.vku.daduyminh.model.RegisterApiResponse;
import com.vku.daduyminh.model.User;
import com.vku.daduyminh.net.RetrofitClient;

import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {

    private static final String TAG = RegisterRepository.class.getSimpleName();

    public LiveData<RegisterApiResponse> getRegisterResponseData(User user) {
        final MutableLiveData<RegisterApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance(LOCALHOST).getApi().createUser(user).enqueue(new Callback<RegisterApiResponse>() {
            @Override
            public void onResponse(retrofit2.Call<RegisterApiResponse> call, Response<RegisterApiResponse> response) {
                Log.d(TAG, "onResponse: Succeeded");

                RegisterApiResponse registerApiResponse = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(registerApiResponse);
                    Log.d(TAG, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<RegisterApiResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
