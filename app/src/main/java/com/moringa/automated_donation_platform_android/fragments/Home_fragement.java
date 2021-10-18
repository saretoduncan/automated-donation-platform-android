package com.moringa.automated_donation_platform_android.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.adapters.CharityListAdapter;
import com.moringa.automated_donation_platform_android.models.charityModel;
import com.moringa.automated_donation_platform_android.viewModel.DonationCharityListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class Home_fragement extends Fragment {
    private List<charityModel> charityList;
    private CharityListAdapter adapter;
    DonationCharityListViewModel viewModel;
    String[] names= {"inua-dada","padUpGirl","educatedGirls"};
    ArrayList<String> convert= new ArrayList<>();
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.mainRecyclerView)
    RecyclerView recyclerView;


    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_fragement, container, false);
        ButterKnife.bind(this,view);

        viewModel = new ViewModelProvider(this).get(DonationCharityListViewModel.class);
        viewModel.makeApiCall();

        viewModel.getCharityListObserver().observe(this, new Observer<List<charityModel>>() {
            @Override
            public void onChanged(List<charityModel> charityModels) {
                if (charityModels!=null){
                    charityList = charityModels;
                    System.out.println(charityList.get(0).getEmail());
//                    adapter.setConvert(charityList);
                    adapter = new CharityListAdapter(getContext(),charityList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);

                }
            }
        });




        return view;
    }
}