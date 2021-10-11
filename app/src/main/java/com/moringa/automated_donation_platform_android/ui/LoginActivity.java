package com.moringa.automated_donation_platform_android.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.moringa.automated_donation_platform_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.loginButton) Button mLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mLoginBtn) {
            Intent intent = new Intent(LoginActivity.this, CharityActivity.class);
            startActivity(intent);
        }
    }
}