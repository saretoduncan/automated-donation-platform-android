package com.moringa.automated_donation_platform_android.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.SessionManager;
import com.moringa.automated_donation_platform_android.fragments.*;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DonorsActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.navbar) BottomNavigationView bottomNav;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.toolbar) View toolbar;
    TextView mDonorName;
    CircleImageView profileImage;
    Home_fragement home_fragement;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors);
        ButterKnife.bind(this);
        mDonorName = (TextView) toolbar.findViewById(R.id.donorNameTextView);
      profileImage= (CircleImageView) findViewById(R.id.donorProfileImage);


        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
        String name = userDetails.get(SessionManager.KEY_NAME);
        String usersId = userDetails.get(SessionManager.KEY_ID);
        mDonorName.setText(name);
        Picasso.get().load(userDetails.get(SessionManager.KEY_IMAGE)).into(profileImage);


        home_fragement= new Home_fragement();
        userId = getIntent().getStringExtra("userID");//get userid

        Bundle bundle = new Bundle();
        bundle.putString("usersId", userId);
        home_fragement.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout1,home_fragement).commit();// opens home fragment when the app starts
        bottomNav.setOnNavigationItemSelectedListener(navListener);// listen to navigation bar button click

    }
    private BottomNavigationView.OnNavigationItemSelectedListener  navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch(item.getItemId()){
                        case R.id.nav_home://home fragemnt

                            selectedFragment = home_fragement;
                            toolbar_visibility_visible();
                            break;
                        case R.id.nav_donations://donation fragment
                            Bundle bundle = new Bundle();
                            bundle.putString("usersId", userId);
                            Toast.makeText(DonorsActivity.this, userId, Toast.LENGTH_SHORT).show();
                            new DonationList_fragment().setArguments(bundle);
                            selectedFragment= new DonationList_fragment();

                            toolbar_visibility_visible();

                            break;
                        case R.id.nav_profile://profile fragment
                            toolbar_visibility_gone();
                            selectedFragment= new Profile_fragment();
                            break;
                        case R.id.nav_exit:
                        AlertDialog.Builder builder = new AlertDialog.Builder(DonorsActivity.this);
                        builder.setMessage("Do you really have to\n" +
                                "log out?");
                        builder.setCancelable(true);
                        builder.setNegativeButton(Html.fromHtml("<font color='#F6E11E'>Yes</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        builder.setPositiveButton(Html.fromHtml("<font color='#000000'>No</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        selectedFragment= home_fragement;
                        break;
                    }

                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout1,selectedFragment).commit();
                    return true;
                }
            };
    public void toolbar_visibility_gone(){
       toolbar.setVisibility(View.GONE);
    }//hide action bar
    public void toolbar_visibility_visible(){
        toolbar.setVisibility(View.VISIBLE);
    }// show action bar
}