package com.moringa.automated_donation_platform_android.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.adapters.BeneficiaryListAdapter;
import com.moringa.automated_donation_platform_android.models.Beneficiary;
import com.moringa.automated_donation_platform_android.network.ApiClient;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBeneficiaryFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.beneficiaryNameEditText) EditText mName;
    @BindView(R.id.testimonialEditText) EditText mTestimonial;
    @BindView(R.id.beneficiaryImageView) ImageView mProfile;
    @BindView(R.id.beneficiaryImageUploadBtn) Button uploadImage;
    @BindView(R.id.addBeneficiary) Button mAddBtn;
    Uri imageUrl = null;
    private Beneficiary mBeneficiary;
    private static final int GALLERY_CODE = 71;


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
//        uploadImage.setOnClickListener(this);
//        uploadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                System.out.println("Hi its me");
//
//
//            }// end onClick
//        });
        return view;
    }

//
//    public void addBeneficiary(){
//        String name = mName.getText().toString();
//        String testimonial = mTestimonial.getText().toString();
//        String image = imageUrl.toString();
//
//        Beneficiary beneficiary = new Beneficiary(name,testimonial,image);
//        Call<Beneficiary> call = ApiClient.getBeneficiaryService().addBeneficiary(1,beneficiary);
//        call.enqueue(new Callback<Beneficiary>() {
//            @Override
//            public void onResponse(Call<Beneficiary> call, Response<Beneficiary> response) {
//                if(response.isSuccessful()){
//                    List<Beneficiary> beneficiaries = new ArrayList<>();
//                    beneficiaries.add(response.body());
//                    mBeneficiary = response.body();
//                    Log.d("added beneficiary",mBeneficiary.getName());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Beneficiary> call, Throwable t) {
//
//            }
//        });
//
//
//    }
//
    private void onUpLoadImage() {
        Intent takePictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
        takePictureIntent.setType("image/*");
        startActivityForResult(takePictureIntent,GALLERY_CODE);
    }

    @Override
    public void onClick(View view) {
      if(view == uploadImage){
//            onUpLoadImage();
        }
        if(view == mAddBtn){
//            addBeneficiary();
        }

    }

//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == GALLERY_CODE && requestCode == Activity.RESULT_OK) {
//            imageUrl = data.getData();
//            mProfile.setImageURI(imageUrl);
//            Toast.makeText(getContext(), "Image saved!!", Toast.LENGTH_LONG).show();
//        }
//        mAddBtn.setOnClickListener(this);
//    }
//
//    public String getEncodedImage(Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), inImage, "Title", null);
//        return path;
//    }
}