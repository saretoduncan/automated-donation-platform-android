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
    @GET("api/admin/getAllNotApproved")//get all not approved
    Call<List<Admin>>adminGetAllNotApproved();
    @GET("api/admin/getallapprove")
    Call<List<Admin>>adminGetAllApproved();//get all approved
    @PATCH("api/admin/approve/{charityId}")//approve charity Organization
    Call<Void>  adminApproveCharity(@Path("charityId") String charityId );
    @DELETE("api/deleteCharityOrganizationByid/{id}")//delete charity Organization
    Call<Void> adminDeleteCharityOrganisation(@Path("id") String charityId);


    @GET("api/admin/approvedCharity/{charityId}")
    Call<Admin> checkIfCharityIsApproved(@Path("charityId") int charityId);
}
