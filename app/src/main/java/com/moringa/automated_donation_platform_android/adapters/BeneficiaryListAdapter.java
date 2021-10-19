package com.moringa.automated_donation_platform_android.adapters;

import static com.cloudinary.android.ResponsiveUrl.Preset.FIT;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.download.DownloadRequestCallback;
import com.moringa.automated_donation_platform_android.R;
import com.moringa.automated_donation_platform_android.models.Beneficiary;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class BeneficiaryListAdapter extends RecyclerView.Adapter<BeneficiaryListAdapter.BeneficiaryViewHolder>{
    List<Beneficiary> mBeneficiary;
    Context context;

    public BeneficiaryListAdapter(List<Beneficiary> mBeneficiary, Context context) {
        this.mBeneficiary = mBeneficiary;
        this.context = context;
    }

    @NonNull
    @Override
    public BeneficiaryListAdapter.BeneficiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beneficiary_list_item,parent,false);
        BeneficiaryListAdapter.BeneficiaryViewHolder viewHolder = new BeneficiaryListAdapter.BeneficiaryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BeneficiaryListAdapter.BeneficiaryViewHolder holder, int position) {
        holder.nameTxt.setText(mBeneficiary.get(position).getName());
        holder.testimonialTxt.setText(mBeneficiary.get(position).getTestimony());
        String imageUrl = mBeneficiary.get(position).getImage();
//        Picasso.get().load(imageUrl).into(holder.beneficiaryImage);
        try {
            imageDisplay(imageUrl,holder.beneficiaryImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mBeneficiary.size();
    }

    public class BeneficiaryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.beneficiaryNameTextView) TextView nameTxt;
        @BindView(R.id.testimonialTxtView) TextView testimonialTxt;
        @BindView(R.id.beneficiaryProfilePic) CircleImageView beneficiaryImage;

        public BeneficiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void imageDisplay(String url, ImageView profile_photo) throws IOException {
        URL newurl = new URL(url);
        Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        profile_photo.setImageBitmap(mIcon_val);
    }
}
