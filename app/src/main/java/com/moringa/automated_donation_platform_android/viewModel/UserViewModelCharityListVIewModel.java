package com.moringa.automated_donation_platform_android.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moringa.automated_donation_platform_android.models.User;
import com.moringa.automated_donation_platform_android.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModelCharityListVIewModel  extends ViewModel {
    final MutableLiveData<User> getUserDetails;
    public UserViewModelCharityListVIewModel(){
        getUserDetails = new MutableLiveData<>();
    }

    public MutableLiveData<User> getGetUserDetailsObserve() {
        return getUserDetails;
    }

    public void makeApiCall(String id){
        Call<User> call = ApiClient.getDonationService().getCharityByUserId(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    getUserDetails.setValue(response.body());
                    System.out.println("userResponse:::"+"successful");
                }
                else {
                    System.out.println("userResponse::"+" not successful");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
