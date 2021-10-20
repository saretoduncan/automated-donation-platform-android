package com.moringa.automated_donation_platform_android.adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.bind.util.ISO8601Utils;
import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.fragments.Payment_Method;
import com.moringa.automated_donation_platform_android.models.Charity;
import com.moringa.automated_donation_platform_android.models.User;
import com.moringa.automated_donation_platform_android.network.ApiClient;
import com.moringa.automated_donation_platform_android.viewModel.UserViewModelCharityListVIewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharityListAdapter extends RecyclerView.Adapter<CharityListAdapter.charityViewHolder>  {
 private Context context;


//    private ArrayList<String> convert;
 private boolean isShow=false;
    Dialog donationDialog;
    List<Charity>convert;
    EditText edAmount;
    Button btnDonateDialog ;
    String name;
    String charityId;
    String userId;
    String charityName;
    public static User charityOrg;


    public CharityListAdapter(Context context, List<Charity> convert, String userId) {
        this.context = context;
        this.convert= convert;
        this.userId = userId;
    }
    public void setConvert(List<Charity> convert) {
        this.convert = convert;
    }

    @NonNull
    @Override
    public CharityListAdapter.charityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent,false);
         donationDialog = new Dialog(this.context);
//       ;
        donationDialog.setContentView(R.layout.layout_donation_dialog);
        return new charityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull charityViewHolder holder, int position) {

        String id  = Integer.toString(this.convert.get(position).getId());
        Call<User> call = ApiClient.getDonationService().getCharityByUserId(Integer.toString(convert.get(position).getUserId()));
        call.enqueue(new Callback<User>() {
          @Override
          public void onResponse(Call<User> call, Response<User> response) {
              if(response.isSuccessful()){
                  System.out.println("userResponse::"+ "is success");
                   holder.charityProfileName.setText(response.body().getName());
                   charityName= holder.charityProfileName.getText().toString();

              }else{
                  System.out.println("userResponse::"+ "is not a success");
              }
          }

          @Override
          public void onFailure(Call<User> call, Throwable t) {

          }

      });







        holder.donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                donationDialog.show();
                isShow=true;
                name= holder.charityProfileName.getText().toString();
                charityId =  id;
                Toast.makeText(view.getContext(), id, Toast.LENGTH_SHORT).show();


            }


        });




    }

    @Override
    public int getItemCount() {

            return convert.size();



    }



    public class charityViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.charityProfileName)
        TextView charityProfileName;
        @BindView(R.id.btnDonate)
        Button donateBtn;

        public charityViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind( this, itemView);

            btnDonateDialog = (Button) donationDialog.findViewById(R.id.btnDonateDialog);
            btnDonateDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    edAmount = (EditText) donationDialog.findViewById(R.id.edit_text_amount);
                    String amountDonated = edAmount.getText().toString();
                    if(!amountDonated.equals("")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("profileName", name);
                        bundle.putString("amount", amountDonated);
                        bundle.putString("charityId", charityId);
                        bundle.putString("userId", userId);
                        Payment_Method payment_method = new Payment_Method();
                        payment_method.setArguments(bundle);

                         ((AppCompatActivity) context)
                                .getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout1, payment_method).commit();

                         donationDialog.dismiss();
                    }
                }
            });

        }
    }

}
