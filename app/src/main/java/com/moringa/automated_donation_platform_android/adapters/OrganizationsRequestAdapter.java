package com.moringa.automated_donation_platform_android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.fragments.admin_home_fragment;
import com.moringa.automated_donation_platform_android.fragments.admin_request_fragment;
import com.moringa.automated_donation_platform_android.models.Admin;
import com.moringa.automated_donation_platform_android.models.Charity;
import com.moringa.automated_donation_platform_android.models.User;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.moringa.automated_donation_platform_android.viewModel.AdminViewModel;
import com.moringa.automated_donation_platform_android.viewModel.UserViewModelCharityListVIewModel;

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
    Context context;
    ProgressBar progressBar;

    public OrganizationsRequestAdapter(List<Admin> admins, Context context, ProgressBar progressBar) {
        this.admins= admins;
        this.context= context;
        this.progressBar = progressBar;
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
        Log.d("donorId",charityId);
        Call<Charity> charityCall= ApiClient.getCharityService().getCharity(Integer.parseInt(charityId));

        charityCall.enqueue(new Callback<Charity>() {
            @Override
            public void onResponse(Call<Charity> call, Response<Charity> response) {
                if(response.isSuccessful()) {
                    System.out.println("charityResponse::: was a success");
                    holder.aboutOrg.setText(response.body().getDescription());
                    int userId= response.body().getUserId();
                  
                    Call<User> userCall = ApiClient.getUserService().getUserDetails(userId);
                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                System.out.println("user.request::: is a success");
                                holder.organisationName.setText(response.body().getName());

                            }else System.out.println("user.request::: is not a success");
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });

                }else System.out.println("charityResponse:::: was not a success");

            }

            @Override
            public void onFailure(Call<Charity> call, Throwable t) {

            }
        });
        holder.approve.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Call<Void>approve = ApiClient.getAdminServices().adminApproveCharity(charityId);
                approve.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if(response.code()==200){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(view.getContext(), "approval success", Toast.LENGTH_SHORT).show();
                            ((AppCompatActivity) context)
                                    .getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayout3, new admin_request_fragment()).commit();

                        }else Toast.makeText(view.getContext(), "approval not a success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }

                });

            }
        });
       holder.cancelApproval.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               progressBar.setVisibility(View.VISIBLE);
               Call<Void> cancelRequest = ApiClient.getAdminServices().adminDeleteCharityOrganisation(charityId);
               cancelRequest.enqueue(new Callback<Void>() {
                   @Override
                   public void onResponse(Call<Void> call, Response<Void> response) {
                       if(response.code()==200){
                           progressBar.setVisibility(View.GONE);

                       }else Toast.makeText(view.getContext(), "delete is not success", Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onFailure(Call<Void> call, Throwable t) {

                   }
               });
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
        @BindView(R.id.organization_Name) TextView organisationName;
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
