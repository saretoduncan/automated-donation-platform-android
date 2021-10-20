package com.moringa.automated_donation_platform_android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.SessionManager;
import com.moringa.automated_donation_platform_android.adapters.DonationListAdapter;
import com.moringa.automated_donation_platform_android.models.DonationModel;
import com.moringa.automated_donation_platform_android.viewModel.DonorDonationListViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DonationList_fragment extends Fragment {
    private DonationListAdapter adapter;//donation list adapter
    @BindView(R.id.donorsRecyclerView)
    RecyclerView recyclerView;
    DonorDonationListViewModel viewModel;
    List<DonationModel> donationList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_donation_list_fragment, container, false);
        ButterKnife.bind(this,view);
        SessionManager sessionManager = new SessionManager(requireContext());
        HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
        String userId= userDetails.get(SessionManager.KEY_ID);
        Toast.makeText(getContext(), userId, Toast.LENGTH_SHORT).show();
      viewModel= new ViewModelProvider(this).get(DonorDonationListViewModel.class);
      viewModel.makeApiCall(userId);
      viewModel.getDonationListObserve().observe(getViewLifecycleOwner(), new Observer<List<DonationModel>>() {
          @Override
          public void onChanged(List<DonationModel> donationModels) {
              if (donationModels == null) {
                  Toast.makeText(getContext(), "not successful", Toast.LENGTH_SHORT).show();
              } else {
                  Toast.makeText(getContext(), "is successful", Toast.LENGTH_SHORT).show();
                  donationList = donationModels;
                  adapter = new DonationListAdapter(donationList);// create new  of donation adapter
                  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                  recyclerView.setLayoutManager(linearLayoutManager);//set recycler layout
                  recyclerView.setAdapter(adapter);
              }
          }
      });


        return view;
    }
}