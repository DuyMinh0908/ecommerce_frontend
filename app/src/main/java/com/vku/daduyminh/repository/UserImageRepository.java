package com.vku.daduyminh.repository;

import static com.vku.daduyminh.utils.Constant.LOCALHOST;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.vku.daduyminh.model.Image;
import com.vku.daduyminh.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserImageRepository {

    private static final String TAG = UserImageRepository.class.getSimpleName();

    public LiveData<Image> getUserImage(int userId) {
        final MutableLiveData<Image> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance(LOCALHOST).getApi().getUserImage(userId).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                Log.d(TAG, "" + response.code());

                Image responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                Log.d(TAG, "Loi roi ban ei: "+ t.getMessage());
            }
        });
        return mutableLiveData;
    }


}
