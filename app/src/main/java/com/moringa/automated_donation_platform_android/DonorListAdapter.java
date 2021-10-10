package com.moringa.automated_donation_platform_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DonorListAdapter extends RecyclerView.Adapter<DonorListAdapter.DonorsViewHolder> {
    List<String> names;
    List<Integer> amount;
    Context context;

    public DonorListAdapter(Context context, List<String> names, List<Integer> amount) {
        this.names = names;
        this.amount = amount;
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
//        holder.donorAmountTxt.setText();
    }

    @Override
    public int getItemCount() {
        return names.size();
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
