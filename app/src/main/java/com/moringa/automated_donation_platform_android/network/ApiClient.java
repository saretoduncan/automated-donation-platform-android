package com.moringa.automated_donation_platform_android.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "https://automateddonationplatform.herokuapp.com/";
    public static Retrofit getRetrofit(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static UserService getUserService(){
        UserService userService = getRetrofit().create(UserService.class);
        return userService;
    }

    public static BeneficiaryService getBeneficiaryService(){
        BeneficiaryService beneficiaryService = getRetrofit().create(BeneficiaryService.class);
        return beneficiaryService;
    }
    public static  DonationService getDonationService(){
        DonationService donationService = getRetrofit().create(DonationService.class);
        return donationService;
    }
}
