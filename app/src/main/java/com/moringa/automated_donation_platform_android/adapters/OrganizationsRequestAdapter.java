package com.moringa.automated_donation_platform_android.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.models.Admin;
import com.moringa.automated_donation_platform_android.models.Charity;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.moringa.automated_donation_platform_android.viewModel.AdminViewModel;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganizationsRequestAdapter extends RecyclerView.Adapter<OrganizationsRequestAdapter.RequestViewHolder> {
    private List<Admin> admins;
    AdminViewModel adminViewModel;

    public OrganizationsRequestAdapter(List<Admin> admins) {
        this.admins= admins;
    }

    @NonNull
    @Override
    public OrganizationsRequestAdapter.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_card_view, parent, false);
      return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        String charityId= this.admins.get(position).getCharityid();
        Call<Charity> charityCall= ApiClient.getCharityService().getCharity(Integer.parseInt(charityId));
        charityCall.enqueue(new Callback<Charity>() {
            @Override
            public void onResponse(Call<Charity> call, Response<Charity> response) {
                if(response.isSuccessful()) {
                    System.out.println("charityResponse::: was a success");
                    holder.aboutOrg.setText(response.body().getDescription());

                }else System.out.println("charityResponse:::: was not a success");

            }

            @Override
            public void onFailure(Call<Charity> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if(this.admins!=null){
            return this.admins.size();
        }
        return 0;
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.organization_about)
        TextView aboutOrg;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.accept) TextView approve;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.reject)TextView cancelApproval;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
