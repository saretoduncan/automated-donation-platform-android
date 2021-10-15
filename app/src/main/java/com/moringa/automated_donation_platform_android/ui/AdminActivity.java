package com.moringa.automated_donation_platform_android.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.fragments.BeneficiariesFragment;
import com.moringa.automated_donation_platform_android.fragments.BeneficiaryProfileFragment;
import com.moringa.automated_donation_platform_android.fragments.HomeFragment;
import com.moringa.automated_donation_platform_android.fragments.admin_home_fragment;
import com.moringa.automated_donation_platform_android.fragments.admin_request_fragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.admin_navbar)
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);
        bottomNav.setOnNavigationItemSelectedListener(navListener);// listen to navigation bar button click
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout3,
                new admin_home_fragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener  navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_admin_Home:
                            selectedFragment = new admin_home_fragment();
                            break;
                        case R.id.nav_admin_Beneficiaries:
                            selectedFragment = new admin_request_fragment();
                            break;

                        case R.id.nav_admin_Logout:
                            AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
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
                            selectedFragment = new admin_home_fragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout3,
                            selectedFragment).commit();
                    return true;
                }
            };
}