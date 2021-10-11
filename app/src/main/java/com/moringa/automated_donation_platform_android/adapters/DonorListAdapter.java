package com.moringa.automated_donation_platform_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.models.Donor;

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
    public DonorListAdapter.DonorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_list_item,parent,false);
        DonorsViewHolder viewHolder = new DonorsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DonorsViewHolder holder, int position) {
        String amt = Integer.toString(donors.get(position).getAmount());
        holder.donorAmountTxt.setText("Ksh. " + amt);
        holder.nameTxt.setText(donors.get(position).getName());
        holder.donorImage.setImageResource(donors.get(position).getImage());
//        String imageUrl = donors.get(position).getImage();
//        Picasso.get().load(imageUrl).into(holder.donorImage);

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
