package com.moringa.automated_donation_platform_android.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.fragments.*;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DonorsActivity extends AppCompatActivity {

@BindView(R.id.navbar)
    BottomNavigationView bottomNav;
@BindView(R.id.toolbar)
View toolbar;

    Home_fragement home_fragement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors);
        ButterKnife.bind(this);
        home_fragement= new Home_fragement();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout1,home_fragement).commit();// opens home fragment when the app starts
        bottomNav.setOnNavigationItemSelectedListener(navListener);// listen to navigation bar button click

    }
    private BottomNavigationView.OnNavigationItemSelectedListener  navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch(item.getItemId()){
                        case R.id.nav_home://home fragemnt

                            selectedFragment = home_fragement;
                            toolbar_visibility_visible();
                            break;
                        case R.id.nav_donations://donation fragment

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
                            builder.setNegativeButton(Html.fromHtml("<font color='#13C1CC'>Yes</font>"), new DialogInterface.OnClickListener() {
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