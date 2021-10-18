package com.moringa.automated_donation_platform_android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.SessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharityProfileFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.charityEmailEditText) EditText mCharityEmail;
    @BindView(R.id.charityPhoneNumberEditText) EditText mPhoneNumber;
    @BindView(R.id.charityAboutEditText) EditText mAbout;
    @BindView(R.id.trustDeedBtn) Button mTrustDeed;

    public CharityProfileFragment() {
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


        SessionManager sessionManager = new SessionManager(getContext());
        HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
        String name = userDetails.get(SessionManager.KEY_EMAIL);
        String phone = userDetails.get(SessionManager.KEY_PHONENUMBER);

        mCharityEmail.setText(name);
        mPhoneNumber.setText(phone);

        return view;
    }

    @Override
    public void onClick(View view) {


    }

}