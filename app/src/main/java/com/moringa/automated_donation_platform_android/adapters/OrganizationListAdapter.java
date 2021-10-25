package com.moringa.automated_donation_platform_android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.fragments.admin_home_fragment;
import com.moringa.automated_donation_platform_android.fragments.admin_request_fragment;
import com.moringa.automated_donation_platform_android.models.Admin;
import com.moringa.automated_donation_platform_android.models.Charity;
import com.moringa.automated_donation_platform_android.models.User;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganizationListAdapter extends RecyclerView.Adapter<OrganizationListAdapter.OrganizationViewHolder>{
    List<Admin> admin;
    Context context;;
    ProgressBar progressBar;
    public OrganizationListAdapter(List<Admin> admins, Context context, ProgressBar progressbar) {
        this.admin = admins;
        this.context= context;
        this.progressBar= progressbar;

    }

    @NonNull
    @Override
    public OrganizationListAdapter.OrganizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.charity_organization_item,parent,false);
        return new OrganizationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationViewHolder holder, int position) {
        progressBar.setVisibility(View.VISIBLE);
        String charityId= this.admin.get(position).getCharityid();
        Call<Charity> charityCall= ApiClient.getCharityService().getCharity(Integer.parseInt(charityId));
        charityCall.enqueue(new Callback<Charity>() {
            @Override
            public void onResponse(Call<Charity> call, Response<Charity> response) {
                if(response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    System.out.println("charityResponse::: was a success");

                    int userId= response.body().getUserId();

                    Call<User> userCall = ApiClient.getUserService().getUserDetails(userId);
                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                System.out.println("user.request::: is a success");
                                holder.profileName.setText(response.body().getName());
                                Picasso.get().load(response.body().getImage()).into(holder.profileImage);

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
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               progressBar.setVisibility(View.VISIBLE);
                Call<Void> cancelRequest = ApiClient.getAdminServices().adminDeleteCharityOrganisation(charityId);

                cancelRequest.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code()==200){
                           progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(view.getContext(), "delete is a success", Toast.LENGTH_SHORT).show();
                            ((AppCompatActivity) context)
                                    .getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayout3, new admin_home_fragment()).commit();
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
        if(admin!=null){
            return admin.size();
        }
        return 0;
    }

    public class OrganizationViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.img_donation_Profile) CircleImageView profileImage;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.profile_name)
        TextView profileName;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.deleteButton)
        TextView deleteButton;
        @SuppressLint("NonConstantResourceId")

        public OrganizationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
