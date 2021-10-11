package com.moringa.automated_donation_platform_android;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moringa.automated_donation_platform_android.fragments.*;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

@BindView(R.id.navbar)
    BottomNavigationView bottomNav;
@BindView(R.id.toolbar)
View toolbar;

    Home_fragement home_fragement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        home_fragement= new Home_fragement();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout1,home_fragement).commit();
    bottomNav.setOnNavigationItemSelectedListener(navListener);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener  navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch(item.getItemId()){
                        case R.id.nav_home:
                            toolbar_visibility_visible();
                            selectedFragment = home_fragement;

                            break;
                        case R.id.nav_donations:
                            toolbar_visibility_visible();
                            selectedFragment= new DonationList_fragment();
                            break;
                        case R.id.nav_profile:
                            toolbar_visibility_gone();
                            selectedFragment= new Profile_fragment();
                            break;
                    }
                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout1,selectedFragment).commit();
                    return true;
                }
            };
    public void toolbar_visibility_gone(){
       toolbar.setVisibility(View.GONE);
    }
    public void toolbar_visibility_visible(){
        toolbar.setVisibility(View.VISIBLE);
    }
}