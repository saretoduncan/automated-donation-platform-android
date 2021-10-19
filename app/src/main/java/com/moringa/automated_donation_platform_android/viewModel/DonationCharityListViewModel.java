package com.moringa.automated_donation_platform_android.viewModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moringa.automated_donation_platform_android.models.DonationModel;
import com.moringa.automated_donation_platform_android.models.charityModel;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.moringa.automated_donation_platform_android.network.DonationService;

import java.util.List;

import kotlin.jvm.internal.MutablePropertyReference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DonationCharityListViewModel extends ViewModel {
    private final MutableLiveData<List<charityModel>> charityList;

    public DonationCharityListViewModel() {
        charityList =new MutableLiveData<>();
    }

    public MutableLiveData<List<charityModel>> getCharityListObserver(){
        return charityList;
  }
    public void makeApiCall(){
    Call<List<charityModel>> call = ApiClient.getDonationService().getAllCharities();
    call.enqueue(new Callback<List<charityModel>>() {
        @Override
        public void onResponse(Call<List<charityModel>> call, Response<List<charityModel>> response) {
                  charityList.setValue(response.body());

            System.out.println("response::::"+ charityList.getValue().get(0).getName());
        }

        @Override
        public void onFailure(Call<List<charityModel>> call, Throwable t) {
            Log.d("live", "nothing to display");
        }
    });
  }
}