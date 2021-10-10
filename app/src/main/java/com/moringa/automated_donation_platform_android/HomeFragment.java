package com.moringa.automated_donation_platform_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    RecyclerView mRecyclerView;
    List<Donor> mDonors;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDonors = new ArrayList<>();
        mDonors.add(new Donor("Duncan",500,R.drawable.pic1));
        mDonors.add(new Donor("Osambo",500,R.drawable.pic2));
        mDonors.add(new Donor("Clare",500,R.drawable.pic3));
        mDonors.add(new Donor("Caroh",500,R.drawable.pic4));
        mDonors.add(new Donor("Duncan",500,R.drawable.pic1));
        mDonors.add(new Donor("Osambo",500,R.drawable.pic2));
        mDonors.add(new Donor("Clare",500,R.drawable.pic3));
        mDonors.add(new Donor("Caroh",500,R.drawable.pic4));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.donorList);
        DonorListAdapter mAdapter = new DonorListAdapter(getContext(),mDonors);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}