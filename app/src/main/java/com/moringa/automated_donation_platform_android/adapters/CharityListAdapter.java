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
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.fragments.Payment_Method;
import com.moringa.automated_donation_platform_android.models.charityModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharityListAdapter extends RecyclerView.Adapter<CharityListAdapter.charityViewHolder> {
 private Context context;


//    private ArrayList<String> convert;
 private boolean isShow=false;
    Dialog donationDialog;
    List<charityModel>convert;
    EditText edAmount;
    Button btnDonateDialog ;
    String name;


    public CharityListAdapter(Context context, List<charityModel>convert) {
        this.context = context;
        this.convert= convert;
    }
    public void setConvert(List<charityModel> convert) {
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
    holder.charityProfileName.setText(this.convert.get(position).getName());
    String charityName = holder.charityProfileName.getText().toString();



        holder.donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                donationDialog.show();
                isShow=true;
                name= charityName;
                Toast.makeText(view.getContext(),  charityName, Toast.LENGTH_SHORT).show();


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
