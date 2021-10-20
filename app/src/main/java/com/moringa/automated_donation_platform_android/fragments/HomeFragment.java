package com.moringa.automated_donation_platform_android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moringa.automated_donation_platform_android.SessionManager;
import com.moringa.automated_donation_platform_android.models.DonationModel;
import com.moringa.automated_donation_platform_android.models.Donor;
import com.moringa.automated_donation_platform_android.adapters.DonorListAdapter;
import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.models.User;
import com.moringa.automated_donation_platform_android.network.ApiClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView mRecyclerView;
    List<Donor> mDonors;
    private int charityId;
    private int userId;
    private User user;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.donorList);
        try {
            SessionManager sessionManager = new SessionManager(getContext());
            HashMap<String,String> userDetails = sessionManager.getCharityDetailsFromSession();
            charityId = Integer.parseInt(userDetails.get(SessionManager.KEY_CHARITYID));
            getNonAnoymousDonors();

            mRecyclerView = (RecyclerView) view.findViewById(R.id.donorList);

        }catch (IOException ex){
            ex.printStackTrace();
        }

        return view;
    }

    public void getNonAnoymousDonors() throws IOException {
        Call<List<DonationModel>> listCall = ApiClient.getCharityService().getNonAnonymousDonorsForACharity(charityId);
        listCall.enqueue(new Callback<List<DonationModel>>() {
            @Override
            public void onResponse(Call<List<DonationModel>> call, Response<List<DonationModel>> response) {
                if(response.isSuccessful()){
                    List<DonationModel> donorList = response.body();

                    for (DonationModel donor: donorList) {
                        userId = Integer.parseInt(donor.getUserid());
                        user = getUserDetails();
                        Donor donorDetails = new Donor(user.getName(), donor.getPaymentmode(),user.getImage());
                        mDonors.add(donorDetails);
                    }

                    DonorListAdapter mAdapter = new DonorListAdapter(getContext(),mDonors);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<DonationModel>> call, Throwable t) {

            }
        });


    }

    public User getUserDetails(){
        Call<User> call = ApiClient.getUserService().getUserDetails(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    user = response.body();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        return user;

    }
}