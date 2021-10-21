package com.moringa.automated_donation_platform_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.models.DonationModel;
import com.moringa.automated_donation_platform_android.models.Donor;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DonorListAdapter extends RecyclerView.Adapter<DonorListAdapter.DonorsViewHolder> {
    List<Donor> donors;
    Context context;

    public DonorListAdapter(Context context, List<Donor> donors) {
        this.donors = donors;
        this.context = context;
    }

    @NonNull
    @Override
    public DonorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_list_item,parent,false);
        DonorsViewHolder viewHolder = new DonorsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DonorsViewHolder holder, int position) {
        try {
            holder.donorAmountTxt.setText(donors.get(position).getAmount());
            String[] name = donors.get(position).getName().trim().split(" ");
            holder.nameTxt.setText(name[0]);
            String imageUrl = donors.get(position).getImage();
            Picasso.get().load(imageUrl).into(holder.donorImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return donors.size();
    }

    public class DonorsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nameTextView) TextView nameTxt;
        @BindView(R.id.donorAmountTextView) TextView donorAmountTxt;
        @BindView(R.id.donorProfilePic) CircleImageView donorImage;
        public DonorsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
