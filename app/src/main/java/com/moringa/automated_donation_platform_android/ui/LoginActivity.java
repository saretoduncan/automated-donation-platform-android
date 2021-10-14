package com.moringa.automated_donation_platform_android.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.moringa.automated_donation_platform_android.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,   AdapterView.OnItemSelectedListener  {
    private String category;
    List<String> categories;
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.loginButton) Button mLoginBtn;
    @BindView(R.id.registerTextView) TextView mSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        categories = new ArrayList<>();
        categories.add(0,"Login As...");
        categories.add("Admin");
        categories.add("Donor");
        categories.add("Charity");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


        mLoginBtn.setOnClickListener(this);
        mSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mLoginBtn && category.equals("Charity")) {
            Intent intent = new Intent(LoginActivity.this, CharityActivity.class);
            startActivity(intent);
        }

        if (v == mLoginBtn && category.equals("Donor")) {
            Intent intent = new Intent(LoginActivity.this, DonorsActivity.class);
            startActivity(intent);
        }

        if (v == mSignup) {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        if (parent.getItemAtPosition(position).equals("Choose Category")){
            //Do anything
        }else{
            String item = parent.getItemAtPosition(position).toString();
            category = item;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

}