package com.moringa.automated_donation_platform_android.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.models.DonationModel;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.moringa.automated_donation_platform_android.viewModel.NewDonationViewModel;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class Payment_Method extends Fragment {
    View toolbar;
    BottomNavigationView bottomNav;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button pay_button;
    Bundle bundle;
    NewDonationViewModel newDonationViewModel;
    TextView backButton,add_card, profileName,amount;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bottomNav=(BottomNavigationView) requireActivity().findViewById(R.id.navbar);
        toolbar=(View) requireActivity().findViewById(R.id.toolbar) ;
        hideToolBar();//hide action and tool bars

        View mView= inflater.inflate(R.layout.fragment_payment__method, container, false);
        radioGroup= (RadioGroup) mView.findViewById(R.id.radioGender);
        pay_button=(Button) mView.findViewById(R.id.pay_button);
         backButton= (TextView) mView.findViewById(R.id.back_button_1);
         add_card = (TextView) mView.findViewById(R.id.add_card);
         profileName=(TextView) mView.findViewById(R.id.text_profile_name);
         amount= (TextView) mView.findViewById(R.id.donationAmount);

         Bundle bundle = getArguments();
         profileName.setText(String.valueOf(bundle.getString("profileName")));
         amount.setText("Ksh " +String.valueOf(bundle.getString("amount")));
         backButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 ((AppCompatActivity) requireContext()).getSupportFragmentManager().beginTransaction()
                         .replace(R.id.frameLayout1, new Home_fragement())
                 .commit();
                 showToolBar();//show action and toolbars
             }
         });
         add_card.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ((AppCompatActivity) requireContext()).getSupportFragmentManager().beginTransaction()
                         .replace(R.id.frameLayout1, new addCard()).addToBackStack(null)
                         .commit();
             }
         });
         pay_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 int selecteId= radioGroup.getCheckedRadioButtonId();
                 radioButton=(RadioButton) mView.findViewById(selecteId);
                 Boolean anonymity;
                 if (radioButton.getText().equals("Anonymous")) {
                     anonymity= false;
                 }else{
                     anonymity=true;
                 }
                 String userId = getArguments().getString("userId");
                 String charityId= getArguments().getString("charityId");
                 String amount = getArguments().getString("amount");

                 DonationModel donationModel = new DonationModel(userId,charityId,anonymity,"monthly",amount);
                 Call<DonationModel> call = ApiClient.getDonationService().submitDonations(donationModel);
                 call.enqueue(new Callback<DonationModel>() {
                     @Override
                     public void onResponse(Call<DonationModel> call, Response<DonationModel> response) {
                         if(response.isSuccessful()){
                             Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                         }else
                             Toast.makeText(getContext(), "not a success", Toast.LENGTH_SHORT).show();
                     }

                     @Override
                     public void onFailure(Call<DonationModel> call, Throwable t) {

                     }
                 });

                 System.out.println("userid::::"+ userId);
                 Toast.makeText(getContext(), userId , Toast.LENGTH_SHORT).show();
             }
         });

        return mView;
    }
//    public void initiateViewModel(DonationModel donation){
//        Call<DonationModel> call = ApiClient.getDonationService().submitDonations(donation);
//        call.enqueue(new Callback<DonationModel>() {
//            @Override
//            public void onResponse(Call<DonationModel> call, Response<DonationModel> response) {
//                    if(response.isSuccessful()){
//                        Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
//                    }else
//                        Toast.makeText(getContext(), "not a success", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<DonationModel> call, Throwable t) {
//
//            }
//        });
//
//    }
    public void hideToolBar(){
        toolbar.setVisibility(View.GONE);//hide bottom navbar
        bottomNav.setVisibility(View.GONE);//hide action bar
    }
    public void showToolBar(){
        toolbar.setVisibility(View.VISIBLE);//show bottom navbar
        bottomNav.setVisibility(View.VISIBLE);//show action bar
    }
}