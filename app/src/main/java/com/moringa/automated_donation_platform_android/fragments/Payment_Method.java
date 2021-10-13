package com.moringa.automated_donation_platform_android.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moringa.automated_donation_platform_android.DonorsActivity;
import com.moringa.automated_donation_platform_android.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class Payment_Method extends Fragment {
    View toolbar;
    BottomNavigationView bottomNav;

    TextView backButton,add_card, profileName,amount;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bottomNav=(BottomNavigationView) requireActivity().findViewById(R.id.navbar);
        toolbar=(View) requireActivity().findViewById(R.id.toolbar) ;
       hideToolBar();//hide action and tool bars
        View view= inflater.inflate(R.layout.fragment_payment__method, container, false);
         backButton= (TextView) view.findViewById(R.id.back_button_1);
         add_card = (TextView) view.findViewById(R.id.add_card);
         profileName=(TextView) view.findViewById(R.id.text_profile_name);
         amount= (TextView) view.findViewById(R.id.donationAmount);
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

        return view;
    }
    public void hideToolBar(){
        toolbar.setVisibility(View.GONE);//hide bottom navbar
        bottomNav.setVisibility(View.GONE);//hide action bar
    }
    public void showToolBar(){
        toolbar.setVisibility(View.VISIBLE);//show bottom navbar
        bottomNav.setVisibility(View.VISIBLE);//show action bar
    }
}