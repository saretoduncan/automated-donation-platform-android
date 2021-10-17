package com.moringa.automated_donation_platform_android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.moringa.automated_donation_platform_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeneficiaryProfileFragment extends Fragment implements View.OnClickListener{


    public BeneficiaryProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_charity_profile, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onClick(View view) {


    }

}