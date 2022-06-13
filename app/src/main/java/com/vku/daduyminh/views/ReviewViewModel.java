package com.vku.daduyminh.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.vku.daduyminh.model.ReviewApiResponse;
import com.vku.daduyminh.repository.ReviewRepository;

public class ReviewViewModel extends ViewModel {

    private final ReviewRepository reviewRepository;

    public ReviewViewModel(  ) {
        reviewRepository = new ReviewRepository();
    }

    public LiveData<ReviewApiResponse> getReviews(int productId) {
        return reviewRepository.getReviews(productId);
    }
}
