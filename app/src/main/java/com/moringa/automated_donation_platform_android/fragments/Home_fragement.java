package com.moringa.automated_donation_platform_android.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moringa.automated_donation_platform_android.MainActivity;
import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.adapter.CharityListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class Home_fragement extends Fragment {
    private CharityListAdapter adapter;
    String[] names= {"inua-dada","padUpGirl","educatedGirls"};
    ArrayList<String> convert= new ArrayList<>();
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.mainRecyclerView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_fragement, container, false);
        ButterKnife.bind(this,view);
        for (int i=0; i<names.length;i++){
            convert.add(names[i]);
        }
        adapter = new CharityListAdapter(getContext(),convert);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}