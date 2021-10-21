        package com.moringa.automated_donation_platform_android.fragments;

        import android.os.Bundle;

        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Toast;

        import com.moringa.automated_donation_platform_android.SessionManager;
        import com.moringa.automated_donation_platform_android.models.DonationModel;
        import com.moringa.automated_donation_platform_android.models.Donor;
        import com.moringa.automated_donation_platform_android.adapters.DonorListAdapter;
        import com.moringa.automated_donation_platform_android.R;
        import com.moringa.automated_donation_platform_android.models.User;
        import com.moringa.automated_donation_platform_android.network.ApiClient;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView mRecyclerView;
    List<Donor> mDonors;
    private int charityId;
    private int userId;
    private User user;
    List<String> userIds = new ArrayList<>();
    List<User> nonAnonymousDonors = new ArrayList<>();
    List<DonationModel> donorList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.donorList);
        try {
            SessionManager sessionManager = new SessionManager(getContext());
            HashMap<String,String> userDetails = sessionManager.getCharityDetailsFromSession();
            charityId = Integer.parseInt(userDetails.get(SessionManager.KEY_CHARITYID));
            getNonAnoymousDonors();

        }catch (IOException ex){
            ex.printStackTrace();
        }

        return view;
    }

    public void getNonAnoymousDonors() throws IOException {
        Call<List<DonationModel>> listCall = ApiClient.getCharityService().getNonAnonymousDonorsForACharity(charityId);
        listCall.enqueue(new Callback<List<DonationModel>>() {
            @Override
            public void onResponse(Call<List<DonationModel>> call, Response<List<DonationModel>> response) {
                if(response.isSuccessful()){
                    donorList = response.body();

                    for (DonationModel theDonors:donorList) {
                        String id = theDonors.getUserid();
                        Log.d("donorId", id);
                        userIds.add(id);
                    }
                    getUserDetails();

                    Toast.makeText(getContext(), "Retrieved non anonymous donors successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DonationModel>> call, Throwable t) {

            }
        });
    }

    public void getUserDetails(){
        for (String id:userIds) {
            Call<User> call = ApiClient.getUserService().getUserDetails(Integer.parseInt(id));
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        User userDetails = response.body();
                        List<Donor> nonAnonymousDonorsList = new ArrayList<>();
                        Log.d("donorId", userDetails.getName());
                        for (DonationModel donor:donorList) {
                            Donor donorDetails = new Donor(userDetails.getName(), donor.getPaymentmode(),userDetails.getImage());
                            Log.d("donorId", donorDetails.getName() +" "+ donorDetails.getAmount());
                            nonAnonymousDonorsList.add(donorDetails);
                        }

                        DonorListAdapter mAdapter = new DonorListAdapter(getContext(),nonAnonymousDonorsList);
                        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                        mRecyclerView.setAdapter(mAdapter);

                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }

    }
}