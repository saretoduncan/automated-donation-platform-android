package com.moringa.automated_donation_platform_android.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.SessionManager;
import com.moringa.automated_donation_platform_android.fragments.BeneficiariesFragment;
import com.moringa.automated_donation_platform_android.fragments.HomeFragment;
import com.moringa.automated_donation_platform_android.fragments.CharityProfileFragment;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CharityActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.bottomNavigation) BottomNavigationView bottomNav;
    TextView mCharityName;
    CircleImageView mProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity);
        ButterKnife.bind(this);
        mCharityName = (TextView) toolbar.findViewById(R.id.charityName);
        mProfilePicture = (CircleImageView) toolbar.findViewById(R.id.charity_profile_image);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
        String name = userDetails.get(SessionManager.KEY_NAME);
        String image = userDetails.get(SessionManager.KEY_IMAGE);

        mCharityName.setText(name);
        try {
            Picasso.get().load(image).into(mProfilePicture);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setSupportActionBar(toolbar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.navHome:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navBeneficiaries:
                    selectedFragment = new BeneficiariesFragment();
                    break;
                case R.id.navProfile:
                    selectedFragment = new CharityProfileFragment();
                    break;
                case R.id.navLogout:
                    AlertDialog.Builder builder = new AlertDialog.Builder(CharityActivity.this);
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
                    selectedFragment = new HomeFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                    selectedFragment).commit();
            return true;
        }
    };
}