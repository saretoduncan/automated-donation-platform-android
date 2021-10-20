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
import com.moringa.automated_donation_platform_android.models.Charity;
import com.moringa.automated_donation_platform_android.network.ApiClient;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharityProfileFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.charityEmailEditText) EditText mCharityEmail;
    @BindView(R.id.charityPhoneNumberEditText) EditText mPhoneNumber;
    @BindView(R.id.charityAboutEditText) EditText mAbout;
    @BindView(R.id.trustDeedBtn) Button mTrustDeed;
    private int userId;
    private Charity mCharity;

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
        try {
            SessionManager sessionManager = new SessionManager(getContext());
            HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
            HashMap<String,String> charityDetails = sessionManager.getCharityDetailsFromSession();
            String name = userDetails.get(SessionManager.KEY_EMAIL);
            String phone = userDetails.get(SessionManager.KEY_PHONENUMBER);
            mAbout.setText(charityDetails.get(SessionManager.KEY_BIO));
            mCharityEmail.setText(name);
            mPhoneNumber.setText(phone);

        }catch (Exception e){
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public void onClick(View view) {


    }

//    public void getCharityDetails(){
//        Call<Charity> call = ApiClient.getCharityService().getCharity(userId);
//        call.enqueue(new Callback<Charity>() {
//            @Override
//            public void onResponse(Call<Charity> call, Response<Charity> response) {
//                if (response.isSuccessful()){
//                    mCharity = response.body();
//                    mAbout.setText(mCharity.getDescription());
//                    SessionManager sessionManager = new SessionManager(getContext());
//                    sessionManager.createCharitySession(mCharity.getDescription(),mCharity.getTrustDeed(),Integer.toString(mCharity.getId()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Charity> call, Throwable t) {
//
//            }
//        });
//    }

}