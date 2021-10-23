package com.moringa.automated_donation_platform_android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


public class Profile_fragment extends Fragment {
    CircleImageView profileImage;
    EditText profileName;
    EditText phoneNumber;
    EditText email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fragment, container, false);
        SessionManager sessionManager = new SessionManager(Objects.requireNonNull(getContext()));
        HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
        String name = userDetails.get(SessionManager.KEY_NAME);
        String usersId = userDetails.get(SessionManager.KEY_ID);
        String mEmail = userDetails.get(SessionManager.KEY_EMAIL);
        String mPhoneNumber= userDetails.get(SessionManager.KEY_PHONENUMBER);
        String mProfileImage= userDetails.get(SessionManager.KEY_IMAGE);

        profileImage = (CircleImageView) view.findViewById(R.id.pro_profile_pic);
        profileName = (EditText)view.findViewById(R.id.profileName);
        email=(EditText) view.findViewById(R.id.editText_mail);
        phoneNumber =(EditText) view.findViewById(R.id.editText_phone);
        profileName.setText(name);
        email.setText(mEmail);
        phoneNumber.setText(mPhoneNumber);
        Picasso.get().load(mProfileImage).into(profileImage);

        return view;
    }
}