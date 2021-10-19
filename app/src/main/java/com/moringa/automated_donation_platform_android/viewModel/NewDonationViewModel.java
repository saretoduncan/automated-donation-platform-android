package com.moringa.automated_donation_platform_android.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moringa.automated_donation_platform_android.models.DonationModel;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.moringa.automated_donation_platform_android.network.DonationService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewDonationViewModel extends ViewModel {
 private final MutableLiveData<DonationModel> newDonation;

    public NewDonationViewModel() {
        this.newDonation = new MutableLiveData<>();
    }
    public MutableLiveData<DonationModel> getNewDonationObserver(){
        return newDonation;
    }
    public void makeApiCall(DonationModel donations ){
        Call<DonationModel> call = ApiClient.getDonationService().submitDonations(donations);
        call.enqueue(new Callback<DonationModel>() {
            @Override
            public void onResponse(Call<DonationModel> call, Response<DonationModel> response) {
                if(response.isSuccessful()){
                    newDonation.postValue(response.body());
                }else{
                    System.out.println("creation not successfull");
                    newDonation.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DonationModel> call, Throwable t) {
                newDonation.postValue(null);
            }
        });
    }
}
