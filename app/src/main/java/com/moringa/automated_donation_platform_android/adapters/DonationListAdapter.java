package com.moringa.automated_donation_platform_android.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.models.Charity;
import com.moringa.automated_donation_platform_android.models.DonationModel;
import com.moringa.automated_donation_platform_android.models.User;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationListAdapter extends RecyclerView.Adapter<DonationListAdapter.DonationListViewHolder> {
    private List<DonationModel> donationList;
    public DonationListAdapter(List<DonationModel> donationList) {
       this.donationList = donationList;
    }

    @NonNull
    @Override
    public DonationListAdapter.DonationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_display,parent,false);
        return new DonationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationListViewHolder holder, int position) {
        System.out.println(this.donationList.get(position).getUserid());
        holder.amountDonated.setText(this.donationList.get(position).getPaymentmode());
        String charId= this.donationList.get(position).getCharityid();
        Call <List<Charity>> call = ApiClient.getDonationService().getAllCharities();
        call.enqueue(new Callback<List<Charity>>() {
            @Override
            public void onResponse(Call<List<Charity>> call, Response<List<Charity>> response) {

                for(Charity charity : response.body()){
                    if (Integer.parseInt(charId)== charity.getId()){
                       Call <User> userCall = ApiClient.getDonationService().getCharityByUserId(Integer.toString(charity.getUserId()));
                       userCall.enqueue(new Callback<User>() {
                           @Override
                           public void onResponse(Call<User> call, Response<User> response) {
                               if (response.isSuccessful()){
                                   Picasso.get().load(response.body().getImage()).into(holder.mProfileImage);
                                   holder.profileName.setText(response.body().getName());

                               }
                           }

                           @Override
                           public void onFailure(Call<User> call, Throwable t) {

                           }
                       });


                    };
                }
            }

            @Override
            public void onFailure(Call<List<Charity>> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }

    public class DonationListViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.total_donated_amount)
        TextView amountDonated;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.img_donation_Profile)
        CircleImageView mProfileImage;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.m_profile_name) TextView profileName;
        public DonationListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
