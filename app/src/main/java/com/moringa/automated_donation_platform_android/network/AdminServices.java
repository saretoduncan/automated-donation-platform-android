package com.moringa.automated_donation_platform_android.network;

import com.moringa.automated_donation_platform_android.models.Admin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminServices {
    @POST("api/admin/newrequest")
    Call<Admin> sendRequestToAdmin(@Body Admin charityRequest);
    @PUT("api/admin/approve/{charityId}")
    Call<Admin> updateApproval(@Path("charityId") String charityId);
//    @GET()
}
