package com.moringa.automated_donation_platform_android.viewModel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moringa.automated_donation_platform_android.models.DonationModel;
import com.moringa.automated_donation_platform_android.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonorDonationListViewModel extends ViewModel {
   private final MutableLiveData<List<DonationModel>> donationList;

    public DonorDonationListViewModel() {
        this.donationList = new MutableLiveData<>();

    }

    public MutableLiveData<List<DonationModel>> getDonationListObserve() {
        return donationList;
    }
    public void makeApiCall(String donorsId){
        Call<List<DonationModel>>  call = ApiClient.getDonationService().getAllDonationsPerDonor(donorsId);
        call.enqueue(new Callback<List<DonationModel>>() {
            @Override
            public void onResponse(Call<List<DonationModel>> call, Response<List<DonationModel>> response) {
                if(response.isSuccessful()){
                    donationList.setValue(response.body());
                    System.out.println("donationList:::"+ " is a success");
                }else System.out.println(("donationList:::"+ " is not a success"));
            }

            @Override
            public void onFailure(Call<List<DonationModel>> call, Throwable t) {
                Log.d("live", "nothing to display");
            }
        });


    }
}
