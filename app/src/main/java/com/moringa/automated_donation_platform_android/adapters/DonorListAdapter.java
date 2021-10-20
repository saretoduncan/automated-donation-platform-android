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

//        holder.donorImage.setImageResource(imageUrl);
        try {
            String amt = donors.get(position).getAmount();
            holder.donorAmountTxt.setText(amt);
            holder.nameTxt.setText(donors.get(position).getName());
            String imageUrl = donors.get(position).getImage();
            Picasso.get().load(imageUrl).into(holder.donorImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(donors == null){
            return 6;
        }else if(donors.size() == 1){
            return 2;
        }
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
