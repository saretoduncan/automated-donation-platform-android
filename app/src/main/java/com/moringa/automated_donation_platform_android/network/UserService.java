package com.moringa.automated_donation_platform_android.network;

import com.moringa.automated_donation_platform_android.models.LoginRequest;
import com.moringa.automated_donation_platform_android.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("authenticate/login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);


}
