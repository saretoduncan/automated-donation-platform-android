package com.moringa.automated_donation_platform_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.automated_donation_platform_android.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharityListAdapter extends RecyclerView.Adapter<CharityListAdapter.charityViewHolder> {
 private Context context;
private List<String> convert;

    public CharityListAdapter(Context context, List<String> convert) {
        this.context = context;
        this.convert= convert;
    }

    @NonNull
    @Override
    public CharityListAdapter.charityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent,false);
        return new charityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull charityViewHolder holder, int position) {
    holder.charityProfileName.setText(this.convert.get(position));
    }

    @Override
    public int getItemCount() {

            return this.convert.size();


    }

    public class charityViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.charityProfileName)
        TextView charityProfileName;
        public charityViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind( this, itemView);
        }
    }
}
