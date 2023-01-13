package com.vku.daduyminh.views;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.vku.daduyminh.model.Otp;
import com.vku.daduyminh.repository.OtpRepository;


public class OtpViewModel extends ViewModel {

    private final OtpRepository otpRepository;

    public OtpViewModel() {
        otpRepository = new OtpRepository();
    }

    public LiveData<Otp> getOtpCode(String token, String email) {
        return otpRepository.getOtpCode(token,email);
    }
}
