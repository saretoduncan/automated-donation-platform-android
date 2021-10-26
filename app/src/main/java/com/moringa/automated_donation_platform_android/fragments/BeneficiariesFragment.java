package com.moringa.automated_donation_platform_android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.SessionManager;
import com.moringa.automated_donation_platform_android.adapters.BeneficiaryListAdapter;
import com.moringa.automated_donation_platform_android.models.Beneficiary;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.moringa.automated_donation_platform_android.network.BeneficiaryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeneficiariesFragment extends Fragment implements  View.OnClickListener{

    RecyclerView mRecyclerView;
    List<Beneficiary> mBeneficiaries;
    @BindView(R.id.addBeneficiaryBtn) Button addBtn;
    @BindView(R.id.progress_circular)
    ProgressBar progressBar;
    private int charityId;

    public BeneficiariesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBeneficiaries = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beneficiaries, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.beneficiaryList);
        ButterKnife.bind(this,view);

        SessionManager sessionManager = new SessionManager(getContext());
        HashMap<String,String> userDetails = sessionManager.getCharityDetailsFromSession();
        String id = userDetails.get(SessionManager.KEY_CHARITYID);
        charityId = Integer.parseInt(id);

        getBeneficiaries();
        BeneficiaryListAdapter mAdapter = new BeneficiaryListAdapter(mBeneficiaries, getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        addBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == addBtn){
            getFragmentManager().beginTransaction().replace(this.getId(),new AddBeneficiaryFragment()).commit();
        }

    }

    public void getBeneficiaries(){
        progressBar.setVisibility(View.VISIBLE);
        BeneficiaryService client = ApiClient.getBeneficiaryService();
        Call<List<Beneficiary>> call =client.getBeneficiariesForACharity(charityId);
        call.enqueue(new Callback<List<Beneficiary>>() {
            @Override
            public void onResponse(Call<List<Beneficiary>> call, Response<List<Beneficiary>> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    mBeneficiaries = response.body();
                    for (Beneficiary beneficiary:mBeneficiaries) {
                        Log.d("My beneficiaries",beneficiary.getName());
                    }
                    BeneficiaryListAdapter mAdapter = new BeneficiaryListAdapter(mBeneficiaries, getContext());
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Beneficiary>> call, Throwable t) {

            }
        });

    }
}