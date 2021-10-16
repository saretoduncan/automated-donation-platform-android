package com.moringa.automated_donation_platform_android.network;

import com.moringa.automated_donation_platform_android.models.LoginRequest;
import com.moringa.automated_donation_platform_android.models.LoginResponse;
import com.moringa.automated_donation_platform_android.models.SignupRequest;
import com.moringa.automated_donation_platform_android.models.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("authenticate/")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("users/")
    Call<SignupResponse> userSignup(@Body SignupRequest signupRequest);
}
