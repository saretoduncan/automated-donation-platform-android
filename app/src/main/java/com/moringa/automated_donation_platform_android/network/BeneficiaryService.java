package com.moringa.automated_donation_platform_android.network;

import com.moringa.automated_donation_platform_android.models.Beneficiary;
import com.moringa.automated_donation_platform_android.models.LoginRequest;
import com.moringa.automated_donation_platform_android.models.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface  BeneficiaryService {
    @POST("api/beneficiary/{charityId}/new")
    Call<Beneficiary> addBeneficiary(@Path("charityId") int charityId, @Body Beneficiary beneficiary);

    @GET("api/beneficiaries")
    Call<List<Beneficiary>> getBeneficiaries();
}
