package com.moringa.automated_donation_platform_android.network;

import com.moringa.automated_donation_platform_android.models.DonationModel;
import com.moringa.automated_donation_platform_android.models.charityModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DonationService {
    @POST("api/donation/new")
    Call <DonationModel> submitDonations(@Body DonationModel donations);
    @GET("api/donations/donors/{donorsId}")
    Call<List<DonationModel>> getAllDonationsPerDonor(// get all donations per donor
            @Path("donorsId") String donorsId
    );
    @GET("api/donations/nonAnonymous/{charityId}")
    Call<List<DonationModel>> getNonAnonymousDonationInfo(//get nonAnonymousDonationInfo
            @Path("charityId") String charityId
    );
    @GET("api/donations/charity/{charityId}")
    Call<List<DonationModel>> getAllDonationPerCharity( // get all donations per charity
            @Path("charityId") String charityId
    );
    @GET("api/donations/charities/all")// get all charities
    Call <List<charityModel>> getAllCharities();



}
