package com.moringa.automated_donation_platform_android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.adapters.OrganizationListAdapter;
import com.moringa.automated_donation_platform_android.adapters.OrganizationsRequestAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class admin_request_fragment extends Fragment {
    @BindView(R.id.requestRecyclerView)
    RecyclerView recyclerView;
    OrganizationsRequestAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_admin_request_fragment, container, false);
        ButterKnife.bind(this,view);
        // Inflate the layout for this fragment
        adapter = new OrganizationsRequestAdapter();// create new  of donation adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//set recycler layout
        recyclerView.setAdapter(adapter);
        return view;
    }
}