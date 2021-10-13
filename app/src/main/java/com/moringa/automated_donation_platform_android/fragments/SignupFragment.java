package com.moringa.automated_donation_platform_android.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.ui.DonorsActivity;
import com.moringa.automated_donation_platform_android.ui.LoginActivity;
import com.moringa.automated_donation_platform_android.ui.SignupActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignupFragment extends Fragment implements  AdapterView.OnItemSelectedListener , View.OnClickListener {
    private String category;
    List<String> categories;
    Button mSignup;
    CallbackFragment callbackFragment;
    Spinner spinner;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories = new ArrayList<>();
        categories.add(0,"Choose Category");
        categories.add("Admin");
        categories.add("Donor");
        categories.add("Charity");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        mSignup = view.findViewById(R.id.signUpButton);
        spinner = view.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        mSignup.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(callbackFragment != null && category.equals("Charity")){
            callbackFragment.changeFragment();
        }else {
            moveToNewActivity();
        }
    }

    public void setCallbackFragment(CallbackFragment callbackFragment){
        this.callbackFragment = callbackFragment;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        if (parent.getItemAtPosition(position).equals("Choose Category")){
            //Do anything
        }else{
            String item = parent.getItemAtPosition(position).toString();
            category = item;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    private void moveToNewActivity () {
        Intent i = new Intent(getActivity(), DonorsActivity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);

    }
}