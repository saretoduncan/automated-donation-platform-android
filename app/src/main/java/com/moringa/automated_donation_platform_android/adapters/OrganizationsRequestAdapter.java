package com.moringa.automated_donation_platform_android.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.automated_donation_platform_android.R;

public class OrganizationsRequestAdapter extends RecyclerView.Adapter<OrganizationsRequestAdapter.RequestViewHolder> {


    @NonNull
    @Override
    public OrganizationsRequestAdapter.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_card_view, parent, false);
      return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
