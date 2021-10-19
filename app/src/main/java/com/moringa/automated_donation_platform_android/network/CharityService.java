package com.moringa.automated_donation_platform_android.network;

import com.moringa.automated_donation_platform_android.models.Beneficiary;
import com.moringa.automated_donation_platform_android.models.Charity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CharityService {
    @POST("api/charity/{userId}")
    Call<Charity> addCharity(@Path("userId") int userId, @Body Charity charity);
}
