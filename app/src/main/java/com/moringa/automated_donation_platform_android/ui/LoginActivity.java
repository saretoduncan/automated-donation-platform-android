package com.moringa.automated_donation_platform_android.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.models.LoginRequest;
import com.moringa.automated_donation_platform_android.models.LoginResponse;
import com.moringa.automated_donation_platform_android.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,   AdapterView.OnItemSelectedListener  {
    private String category;
    List<String> categories;
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.loginButton) Button mLoginBtn;
    @BindView(R.id.registerTextView) TextView mSignup;
    @BindView(R.id.emailEditText) EditText email;
    @BindView(R.id.passwordEditText) EditText password;
    @BindView(R.id.forgotPasswordTextView) TextView forgotPassword;

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
        if (v == mLoginBtn && category.equals("Charity") && isValidCredentials()) {
            login();
            Intent intent = new Intent(LoginActivity.this, CharityActivity.class);
            startActivity(intent);
        }

        if (v == mLoginBtn && category.equals("Donor") && isValidCredentials()) {
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
            Toast.makeText(this, "Please choose a category", Toast.LENGTH_SHORT).show();
        }else{
            String item = parent.getItemAtPosition(position).toString();
            category = item;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    public void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login successful.",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Login failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "throwable"+ t.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidCredentials() {
        String mEmail = email.getText().toString().trim();
        String mPassword = password.getText().toString().trim();

        if (mEmail.equals("")) {
            email.setError("Please enter your email");
            return false;
        }
        if (mPassword.equals("")) {
            password.setError("Password cannot be blank");
            return false;
        }
        return true;
    }

}