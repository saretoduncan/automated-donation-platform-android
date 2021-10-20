package com.moringa.automated_donation_platform_android.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.Url;
import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.SessionManager;
import com.moringa.automated_donation_platform_android.models.Beneficiary;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.moringa.automated_donation_platform_android.ui.LoginActivity;
import com.squareup.picasso.Picasso;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBeneficiaryFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.beneficiaryNameEditText) EditText mName;
    @BindView(R.id.testimonialEditText) EditText mTestimonial;
    @BindView(R.id.beneficiaryImageView) ImageView mProfile;
    @BindView(R.id.beneficiaryImageUploadBtn) Button uploadImage;
    @BindView(R.id.addBeneficiary) Button mAddBtn;
    private Uri imageUri;
    private String imagePath;
    private Beneficiary mBeneficiary;
    private int charityId;
    private static final String TAG = "Upload ###";


    public AddBeneficiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_beneficiary, container, false);
        ButterKnife.bind(this,view);
        MediaManager.init(getContext());

        SessionManager sessionManager = new SessionManager(getContext());
        HashMap<String,String> userDetails = sessionManager.getCharityDetailsFromSession();
        String id = userDetails.get(SessionManager.KEY_CHARITYID);
        charityId = Integer.parseInt(id);
        uploadImage.setOnClickListener(this);
        return view;
    }


    public void addBeneficiary(){
        String name = mName.getText().toString();
        String testimonial = mTestimonial.getText().toString();

        Beneficiary beneficiary = new Beneficiary(name,testimonial,imagePath);
        Call<Beneficiary> call = ApiClient.getBeneficiaryService().addBeneficiary(charityId,beneficiary);
        call.enqueue(new Callback<Beneficiary>() {
            @Override
            public void onResponse(Call<Beneficiary> call, Response<Beneficiary> response) {
                if(response.isSuccessful()){
                    List<Beneficiary> beneficiaries = new ArrayList<>();
                    beneficiaries.add(response.body());
                    mBeneficiary = response.body();
                    Log.d("added beneficiary",mBeneficiary.getName());
                    Toast.makeText(getContext(), "Beneficiary Added successfully", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new BeneficiariesFragment()).commit();
                }
            }

            @Override
            public void onFailure(Call<Beneficiary> call, Throwable t) {

            }
        });


    }

    @Override
    public void onClick(View view) {
      if(view == uploadImage){
          requestPermission();
          Log.d(TAG, ": "+"request permission");
        }
        if(view == mAddBtn){
            uploadImageToCloudinary();
        }
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
                addBeneficiary();
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

    private void onUpLoadImage() {
        Intent takePictureIntent = new Intent(Intent.ACTION_PICK);
        takePictureIntent.setType("image/*");
        someActivityResultLauncher.launch(takePictureIntent);
        mAddBtn.setOnClickListener(this);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        imageUri=data.getData();

                        mProfile.setImageURI(imageUri);

                    }
                }
            });

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        {
            onUpLoadImage();
        }else
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },0);
        }
    }

}