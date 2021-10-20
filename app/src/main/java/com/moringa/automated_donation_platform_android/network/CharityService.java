package com.moringa.automated_donation_platform_android.network;

import com.moringa.automated_donation_platform_android.models.Beneficiary;
import com.moringa.automated_donation_platform_android.models.Charity;
import com.moringa.automated_donation_platform_android.models.DonationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CharityService {
    @POST("api/charity/{userId}")
    Call<Charity> addCharity(@Path("userId") int userId, @Body Charity charity);

    @GET("api/charities/{userId}")
    Call<Charity> getCharity(@Path("userId") int userId);

    @GET("api/donations/nonAnonymous/{charityId}")
    Call<List<DonationModel>> getNonAnonymousDonorsForACharity(@Path("charityId") int charityId);
}
