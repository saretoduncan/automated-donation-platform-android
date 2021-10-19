package com.moringa.automated_donation_platform_android.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.SessionManager;
import com.moringa.automated_donation_platform_android.models.User;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.moringa.automated_donation_platform_android.ui.DonorsActivity;
import com.moringa.automated_donation_platform_android.ui.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupFragment extends Fragment implements  AdapterView.OnItemSelectedListener , View.OnClickListener {
    private String category;
    List<String> categories;
    CallbackFragment callbackFragment;
    @BindView(R.id.signUpButton) Button mSignup;
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.nameEditText) EditText mName;
    @BindView(R.id.emailEditText) EditText mEmail;
    @BindView(R.id.passwordEditText) EditText mPassword;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPassword;
    @BindView(R.id.phoneEditText) EditText mPhoneNumber;
    @BindView(R.id.uploadImg) Button uploadImage;
    @BindView(R.id.loginTextView) TextView loginPage;

    Uri imageUri = null;
    String imagePath;
    private static final int GALLERY_CODE = 71;
    private static final String TAG = "Upload ###";


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories = new ArrayList<>();
        categories.add(0,"Choose Category");
        categories.add("Admin");
        categories.add("Donor");
        categories.add("Charity");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this,view);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        MediaManager.init(getContext());
        uploadImage.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == uploadImage){
            onUpLoadImage();
        }
        if(view == mSignup){
            try {
                uploadImageToCloudinary();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setCallbackFragment(CallbackFragment callbackFragment){
        this.callbackFragment = callbackFragment;
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

    private void moveToNewActivity () {
        Intent i = new Intent(getActivity(), DonorsActivity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);

    }

    public void registerUser(User signupRequest){
        Call<User> signupResponseCall = ApiClient.getUserService().userSignup(signupRequest);
        signupResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if(callbackFragment != null && category.equals("Charity")){
                        User user = response.body();
                        SessionManager sessionManager = new SessionManager(getContext());
                        sessionManager.createLoginSession(user.getName(),user.getEmail(),user.getPhone_number(),user.getCategories(),user.getImage(),Integer.toString(user.getId()));
                        callbackFragment.changeFragment();
                    }
                    Toast.makeText(getContext(), "Authentication Successful", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getContext(), "An error occured please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private User validateUser(){
        final String name = mName.getText().toString().trim();
        final String phoneNumber = mPhoneNumber.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        final String confirmPassword = mConfirmPassword.getText().toString().trim();

        boolean validEmail = isValidEmail(email);
        boolean validName = isValidName(name);
        boolean validPhoneNumber = isValidPhoneNumber(phoneNumber);
        boolean validPassword = isValidPassword(password, confirmPassword);

        if (!validEmail || !validName || !validPhoneNumber || !validPassword) return null;

        User signupRequest = new User(name,email,password,category,phoneNumber, imagePath);

        return signupRequest;
    }

    public boolean isValidEmail(String email){
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if(!isGoodEmail){
            mEmail.setError("Please enter a valid email address");
            return false;
        }else {
            return isGoodEmail;
        }
    }

    public boolean isValidName(String name){
        if(name.equals("")){
            mName.setError("Please enter your name");
            return false;
        }else {
            return true;
        }
    }

    public boolean isValidPhoneNumber(String phone){
        if(phone.equals("")){
            mPhoneNumber.setError("Please enter your phone number");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword){
        if(password.length() < 6){
            mPassword.setError("Please create a password containing at least 6 characters");
            return false;
        }else if(!password.equals(confirmPassword)){
            mPassword.setError("Passwords do not match!");
            return false;
        }else {
            return true;
        }
    }

    private void onUpLoadImage() {
        Intent takePictureIntent = new Intent(Intent.ACTION_PICK);
        takePictureIntent.setType("image/*");
        startActivityForResult(takePictureIntent,0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK ){
            if(data == null){
                Toast.makeText(getContext(), "Unable to choose image", Toast.LENGTH_SHORT).show();
                return;
            }
            imageUri = data.getData();
            imagePath = getEncodedImage(imageUri);
            uploadImage.setText(imagePath);
        }
        try {
            mSignup.setOnClickListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getEncodedImage(Uri uri) {
        String[] projections = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(getActivity().getApplicationContext(),uri,projections,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();
        int col_idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(col_idx);
        cursor.close();
        return result;
    }

    public void uploadImageToCloudinary(){

        MediaManager.get().upload(imageUri).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                Log.d(TAG, "onStart: "+"started");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                Log.d(TAG, "onStart: "+"uploading");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                Log.d(TAG, "onStart: "+"usuccess");
                imagePath = resultData.get("url").toString();
                registerUser(validateUser());
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Log.d(TAG, "onStart: "+error);
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                Log.d(TAG, "onStart: "+error);
            }
        }).dispatch();

    }

}