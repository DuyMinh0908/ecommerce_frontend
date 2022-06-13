package com.vku.daduyminh.repository;

import static com.vku.daduyminh.utils.Constant.LOCALHOST;
import static com.vku.daduyminh.utils.Constant.LOCALHOST_IMG_PROCESS;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vku.daduyminh.model.ProductApiResponse;
import com.vku.daduyminh.net.RetrofitClient;
import com.vku.daduyminh.net.RetrofitClientImageProcess;
import com.vku.daduyminh.storage.LoginUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchByImageRepository {
    private static final String TAG = SearchByImageRepository.class.getSimpleName();
    private final  Application application;

    public SearchByImageRepository(Application application) {
        this.application = application;
    }

    public LiveData<ProductApiResponse> searchByImage(String pathname) {
        final MutableLiveData<ProductApiResponse> mutableLiveData = new MutableLiveData<>();

        File file = new File(pathname);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part photo = MultipartBody.Part.createFormData("filename", file.getName(), requestFile);

        RetrofitClientImageProcess.getInstance(LOCALHOST_IMG_PROCESS).getApi().searchProductByImage(photo).enqueue(new Callback<ProductApiResponse>() {
            @Override
            public void onResponse(Call<ProductApiResponse> call, Response<ProductApiResponse> response) {
                ProductApiResponse productApiResponse = response.body();
                if (response.body() != null) {
                    mutableLiveData.setValue(productApiResponse);
                    Log.d(TAG, String.valueOf(response.body().getProducts()));
                }
                else {
                    Log.d(TAG, "onResponse: response null roi kia");
                }
            }

            @Override
            public void onFailure(Call<ProductApiResponse> call, Throwable t) {
                Log.d(TAG,"Co loi xay ra");
            }
        });

        return mutableLiveData;
    }
}