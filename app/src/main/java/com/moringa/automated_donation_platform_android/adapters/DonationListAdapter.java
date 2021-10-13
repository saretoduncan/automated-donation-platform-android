package com.moringa.automated_donation_platform_android.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.automated_donation_platform_android.R;

public class DonationListAdapter extends RecyclerView.Adapter<DonationListAdapter.DonationListViewHolder> {
    @NonNull
    @Override
    public DonationListAdapter.DonationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_display,parent,false);
        return new DonationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 12;
    }

    public class DonationListViewHolder extends RecyclerView.ViewHolder {
        public DonationListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
