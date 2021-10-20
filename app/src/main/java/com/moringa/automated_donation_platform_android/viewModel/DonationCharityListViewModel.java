package com.moringa.automated_donation_platform_android.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moringa.automated_donation_platform_android.models.Charity;
import com.moringa.automated_donation_platform_android.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DonationCharityListViewModel extends ViewModel {
    private final MutableLiveData<List<Charity>> charityList;

    public DonationCharityListViewModel() {
        charityList =new MutableLiveData<>();
    }

    public MutableLiveData<List<Charity>> getCharityListObserver(){
        return charityList;
  }
    public void makeApiCall(){
    Call<List<Charity>> call = ApiClient.getDonationService().getAllCharities();
    call.enqueue(new Callback<List<Charity>>() {
        @Override
        public void onResponse(Call<List<Charity>> call, Response<List<Charity>> response) {
                  charityList.setValue(response.body());

            System.out.println("response::::"+ charityList.getValue().get(0).getUserId());
        }

        @Override
        public void onFailure(Call<List<Charity>> call, Throwable t) {

        }


    });
  }
}