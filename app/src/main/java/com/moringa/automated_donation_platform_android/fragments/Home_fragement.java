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
import android.widget.Toast;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.adapters.CharityListAdapter;
import com.moringa.automated_donation_platform_android.models.Charity;
import com.moringa.automated_donation_platform_android.models.User;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.moringa.automated_donation_platform_android.viewModel.DonationCharityListViewModel;

import java.util.ArrayList;
import java.util.List;

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
    String[] names= {"inua-dada","padUpGirl","educatedGirls"};
    ArrayList<String> convert= new ArrayList<>();
    List<User> users;
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

        viewModel.getCharityListObserver().observe(this, new Observer<List<Charity>>() {
            @Override
            public void onChanged(List<Charity> charityModels) {
                if (charityModels!=null){
                    charityList = charityModels;
                    Bundle bundle=getArguments();
                    String userId =bundle.getString("usersId");
                    adapter = new CharityListAdapter(getContext(),charityList,userId);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);

                    Toast.makeText(getContext(), userId, Toast.LENGTH_SHORT).show();

                }
            }
        });
//        Call<List<User>> call = ApiClient.getDonationService().getAllUsers();
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if(response.isSuccessful()){
//                    users= response.body();
//                    System.out.println("userResponse" + "is success");
//                }
//                else System.out.println("userResponse"+ " not a success");
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//
//            }
//        });
//
//        System.out.println(users.get(0).getName());




        return view;
    }
}