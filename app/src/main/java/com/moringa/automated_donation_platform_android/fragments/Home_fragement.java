package com.moringa.automated_donation_platform_android.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.SessionManager;
import com.moringa.automated_donation_platform_android.adapters.CharityListAdapter;
import com.moringa.automated_donation_platform_android.models.Charity;
import com.moringa.automated_donation_platform_android.models.User;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.moringa.automated_donation_platform_android.viewModel.DonationCharityListViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class Home_fragement extends Fragment {
    private List<Charity> charityList;
    private CharityListAdapter adapter;
    DonationCharityListViewModel viewModel;
    String usersId;
    ArrayList<String> convert= new ArrayList<>();
    List<User> users;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.mainRecyclerView)
    RecyclerView recyclerView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.progress_circular)
    ProgressBar progressBar;


    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_fragement, container, false);
        ButterKnife.bind(this,view);
        SessionManager sessionManager = new SessionManager(requireContext());
        HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
        String name = userDetails.get(SessionManager.KEY_NAME);


        viewModel = new ViewModelProvider(this).get(DonationCharityListViewModel.class);
        viewModel.makeApiCall();
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getCharityListObserver().observe(this, new Observer<List<Charity>>() {

            @Override
            public void onChanged(List<Charity> charityModels) {
                if (charityModels!=null){

                    charityList = charityModels;
                    usersId = userDetails.get(SessionManager.KEY_ID);

                    adapter = new CharityListAdapter(getContext(),charityList,usersId);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), usersId, Toast.LENGTH_SHORT).show();

                }
            }
        });





        return view;
    }
}