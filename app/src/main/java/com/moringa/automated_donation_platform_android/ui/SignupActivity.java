package com.moringa.automated_donation_platform_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.fragments.CallbackFragment;
import com.moringa.automated_donation_platform_android.fragments.CharityApprovalFragment;
import com.moringa.automated_donation_platform_android.fragments.HomeFragment;
import com.moringa.automated_donation_platform_android.fragments.SignupFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements CallbackFragment {

//    @BindView(R.id.spinner) Spinner spinner;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        addFragment();
    }

    public void addFragment(){
        SignupFragment fragment = new SignupFragment();
        fragment.setCallbackFragment(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.signupFragment,fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragment(){
        fragment = new CharityApprovalFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.signupFragment,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void changeFragment() {
        replaceFragment();
    }
}