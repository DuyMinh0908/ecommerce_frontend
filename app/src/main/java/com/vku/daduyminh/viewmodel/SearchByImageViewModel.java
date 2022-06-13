package com.vku.daduyminh.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.vku.daduyminh.model.ProductApiResponse;
import com.vku.daduyminh.repository.SearchByImageRepository;

import okhttp3.ResponseBody;

public class SearchByImageViewModel extends AndroidViewModel {
    private final SearchByImageRepository searchByImageRepository;

        public SearchByImageViewModel(@NonNull Application application){
            super(application);
           searchByImageRepository = new SearchByImageRepository(application);

        }
    public LiveData<ProductApiResponse> searchPhoto(String pathname) {
        return searchByImageRepository.searchByImage(pathname);
    }

}
