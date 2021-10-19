package com.moringa.automated_donation_platform_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.SessionManager;
import com.moringa.automated_donation_platform_android.fragments.Payment_Method;
import com.moringa.automated_donation_platform_android.models.LoginRequest;
import com.moringa.automated_donation_platform_android.models.LoginResponse;
import com.moringa.automated_donation_platform_android.models.User;
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
    ProgressDialog progressDialog;
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
        progressDialog = new ProgressDialog(this);

        mLoginBtn.setOnClickListener(this);
        mSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mLoginBtn){
            progressDialog.show();
            progressDialog.setMessage("logging in...");
            login();
            progressDialog.dismiss();
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

        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        User loginRequest = new User(mail,pass,category);

        Call<User> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login successful.",Toast.LENGTH_SHORT).show();
                    User user = response.body();
                    SessionManager sessionManager = new SessionManager(LoginActivity.this);

                    sessionManager.createLoginSession(user.getName(),user.getEmail(),user.getPhone_number(),user.getCategories(),user.getImage(),Integer.toString(user.getId()));

                    Intent intent = null;
                    switch(category) {
                        case "Charity":
                            intent = new Intent(LoginActivity.this, CharityActivity.class);
                            startActivity(intent);
                            break;
                        case "Donor":
//
                            intent = new Intent(LoginActivity.this, DonorsActivity.class);
                            intent.putExtra("userID", Integer.toString(user.getId()));//get user id
                            startActivity(intent);
                            break;

                        case "Admin":
                            intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Login failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
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