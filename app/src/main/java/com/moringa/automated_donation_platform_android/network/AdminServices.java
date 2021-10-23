package com.moringa.automated_donation_platform_android.network;

import com.moringa.automated_donation_platform_android.models.Admin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminServices {
    @POST("api/admin/newrequest")//make approval Request
    Call<Admin> sendRequestToAdmin(@Body Admin charityRequest);
    @GET("api/admin/getallcharites")// get all charities
    Call<List<Admin>>adminGetAllCharities();
    @PATCH("api/admin/approve/{charityId}")//approve charity Organization
    Call<Admin>  adminApproveCharity(@Path("charityId") String charityId );
    @DELETE("api/deleteCharityOrganizationByid/{id}")//delete charity Organization
    Call<Void> adminDeleteCharityOrganisation(@Path("id") String charityId);
}
