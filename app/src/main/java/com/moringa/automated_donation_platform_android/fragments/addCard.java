package com.moringa.automated_donation_platform_android.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moringa.automated_donation_platform_android.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;


public class addCard extends Fragment {

@BindView(R.id.text_back_button)
    TextView backButton;
@BindView(R.id.save_button)
    Button save_button;
@BindView(R.id.edit_card_number)
    EditText cardNumber;
@BindView(R.id.expiry_date)
    EditText expiryDate;
@BindView(R.id.cvc)
    EditText cvcNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_card, container, false);
        ButterKnife.bind(this,view);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity) requireContext()).getSupportFragmentManager().popBackStack();//going back to the previous fragment
            }
        });

        return  view;
    }
}