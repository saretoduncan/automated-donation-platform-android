package com.moringa.automated_donation_platform_android.network;

import com.moringa.automated_donation_platform_android.models.LoginRequest;
import com.moringa.automated_donation_platform_android.models.LoginResponse;
import com.moringa.automated_donation_platform_android.models.SignupRequest;
import com.moringa.automated_donation_platform_android.models.SignupResponse;
import com.moringa.automated_donation_platform_android.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("/api/users/new")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("/api/users/register")
    Call<User> userSignup(@Body User signupRequest);
}
