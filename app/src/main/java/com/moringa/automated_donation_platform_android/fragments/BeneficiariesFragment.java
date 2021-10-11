package com.moringa.automated_donation_platform_android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.adapters.BeneficiaryListAdapter;
import com.moringa.automated_donation_platform_android.adapters.DonorListAdapter;
import com.moringa.automated_donation_platform_android.models.Beneficiary;
import com.moringa.automated_donation_platform_android.models.Donor;

import java.util.ArrayList;
import java.util.List;

public class BeneficiariesFragment extends Fragment {

    RecyclerView mRecyclerView;
    List<Beneficiary> mBeneficiaries;

    public BeneficiariesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBeneficiaries = new ArrayList<>();
        mBeneficiaries.add(new Beneficiary("Nelly Nayoma","“I can now concentrate in class, I find no reason to miss\n" +
                " classes during my periods, I can stand in front of my \n" +
                "classmates and solve a math problem, I have developed a love\n" +
                " for soccer, and I can do all of this without thinking of my \n" +
                "period when they arrive,” ", R.drawable.pic1));
        mBeneficiaries.add(new Beneficiary("Akura rakera","“I can now concentrate in class, I find no reason to miss\n" +
                " classes during my periods, I can stand in front of my \n" +
                "classmates and solve a math problem, I have developed a love\n" +
                " for soccer, and I can do all of this without thinking of my \n" +
                "period when they arrive,” ",R.drawable.pic2));
        mBeneficiaries.add(new Beneficiary("Clare Limo","“I can now concentrate in class, I find no reason to miss\n" +
                " classes during my periods, I can stand in front of my \n" +
                "classmates and solve a math problem, I have developed a love\n" +
                " for soccer, and I can do all of this without thinking of my \n" +
                "period when they arrive,” ",R.drawable.pic3));
        mBeneficiaries.add(new Beneficiary("Duncan Moiyo","“I can now concentrate in class, I find no reason to miss\n" +
                " classes during my periods, I can stand in front of my \n" +
                "classmates and solve a math problem, I have developed a love\n" +
                " for soccer, and I can do all of this without thinking of my \n" +
                "period when they arrive,” ",R.drawable.pic4));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beneficiaries, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.beneficiaryList);
        BeneficiaryListAdapter mAdapter = new BeneficiaryListAdapter(mBeneficiaries, getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}