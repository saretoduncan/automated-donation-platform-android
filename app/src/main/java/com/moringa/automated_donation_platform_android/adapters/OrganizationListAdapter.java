package com.moringa.automated_donation_platform_android.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.automated_donation_platform_android.R;

public class OrganizationListAdapter extends RecyclerView.Adapter<OrganizationListAdapter.OrganizationViewHolder>{

    @NonNull
    @Override
    public OrganizationListAdapter.OrganizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.charity_organization_item,parent,false);
        return new OrganizationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class OrganizationViewHolder extends RecyclerView.ViewHolder {
        public OrganizationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
