package com.moringa.automated_donation_platform_android.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
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

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.models.Beneficiary;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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

        uploadImage.setOnClickListener(this);

        return view;
    }


    public void addBeneficiary(){
        String name = mName.getText().toString();
        String testimonial = mTestimonial.getText().toString();
        String image = imagePath;

        Beneficiary beneficiary = new Beneficiary(name,testimonial,image);
        Call<Beneficiary> call = ApiClient.getBeneficiaryService().addBeneficiary(1,beneficiary);
        call.enqueue(new Callback<Beneficiary>() {
            @Override
            public void onResponse(Call<Beneficiary> call, Response<Beneficiary> response) {
                if(response.isSuccessful()){
                    List<Beneficiary> beneficiaries = new ArrayList<>();
                    beneficiaries.add(response.body());
                    mBeneficiary = response.body();
                    Log.d("added beneficiary",mBeneficiary.getName());

                }
            }

            @Override
            public void onFailure(Call<Beneficiary> call, Throwable t) {

            }
        });


    }

    private void onUpLoadImage() {
        Intent takePictureIntent = new Intent(Intent.ACTION_PICK);
        takePictureIntent.setType("image/*");
        startActivityForResult(takePictureIntent,0);
    }

    @Override
    public void onClick(View view) {
      if(view == uploadImage){
            onUpLoadImage();
        }
        if(view == mAddBtn){
            addBeneficiary();
        }
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
            mProfile.setImageURI(imageUri);
            Toast.makeText(getContext(), "Image upload successful", Toast.LENGTH_SHORT).show();
        }
        mAddBtn.setOnClickListener(this);
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
}