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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.SessionManager;
import com.moringa.automated_donation_platform_android.models.Admin;
import com.moringa.automated_donation_platform_android.models.Charity;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.moringa.automated_donation_platform_android.ui.DonorsActivity;
import com.moringa.automated_donation_platform_android.ui.LoginActivity;
import com.moringa.automated_donation_platform_android.viewModel.AdminViewModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharityApprovalFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.charityBioEditText) EditText mBio;
    @BindView(R.id.uploadTrustDeed) Button uploadDeed;
    @BindView(R.id.approvalBtn) Button sendBtn;
    @BindView(R.id.uploadTrustDeedTxtView) TextView deedTxtView;
    private String trustDeedPath;
    private Uri trustDeedUri;
    private int userId;
    AdminViewModel viewModel;
    @BindView(R.id.progress_circular) ProgressBar progressBar;
    private static final String TAG = "Upload ###";

    public CharityApprovalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charity_approval, container, false);
        try {
            ButterKnife.bind(this,view);

            SessionManager sessionManager = new SessionManager(getContext());
            HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
            String id = userDetails.get(SessionManager.KEY_ID);
            userId = Integer.parseInt(id);

//            MediaManager.init(getContext());
            uploadDeed.setOnClickListener(this);

        }catch (Exception ex){
            ex.printStackTrace();
        }


        return view;
    }

    public void addCharity(){
        String bio = mBio.getText().toString().trim();
        Charity charity = new Charity(bio,trustDeedPath,userId);

        Call<Charity> charityCall = ApiClient.getCharityService().addCharity(userId,charity);
        charityCall.enqueue(new Callback<Charity>() {
            @Override
            public void onResponse(Call<Charity> call, Response<Charity> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "Charity approval request sent!", Toast.LENGTH_SHORT).show();
                    Charity charityResponse = response.body();
                    SessionManager sessionManager = new SessionManager(getContext());
                    sessionManager.createCharitySession(charityResponse.getDescription(),charityResponse.getTrustDeed(),Integer.toString(charityResponse.getId()));
                    moveToNewActivity();
                }
            }

            @Override
            public void onFailure(Call<Charity> call, Throwable t) {

            }
        });

    }
    public void sendApprovalRequest(){
        SessionManager sessionManager = new SessionManager(requireContext());
        HashMap<String,String> userDetails = sessionManager.getCharityDetailsFromSession();
        String charityId = userDetails.get(SessionManager.KEY_CHARITYID);
        Log.d("donorId",charityId);
        viewModel = new ViewModelProvider(this). get(AdminViewModel.class);
        Admin admin = new Admin(charityId);
        viewModel.sendApprovalRequest(admin);
        viewModel.getApproveCharity_requestObserve().observe(this, new Observer<Admin>() {
            @Override
            public void onChanged(Admin admin) {
                if(admin!=null){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"request sent", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(getContext(), "request not sent", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void moveToNewActivity () {
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View view) {
        if(view == uploadDeed){
            try {
                requestPermission();
            }catch (Exception e){{
                e.printStackTrace();
            }}

        }
        if (view == sendBtn){
            try {
                progressBar.setVisibility(View.VISIBLE);
                uploadPdfToCloudinary();
                sendApprovalRequest();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private void onUpLoadPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);
        sendBtn.setOnClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                 trustDeedUri= result.getData();
                 deedTxtView.setText(getEncodedImage(trustDeedUri));
            }
        }
    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        {
            onUpLoadPdf();
        }else
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },1);
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

    public void uploadPdfToCloudinary(){
        MediaManager.get().upload(trustDeedUri).option("format","png").callback(new UploadCallback() {
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
                trustDeedPath = resultData.get("url").toString();

                addCharity();
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