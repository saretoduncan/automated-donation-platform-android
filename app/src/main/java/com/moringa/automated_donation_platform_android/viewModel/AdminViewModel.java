package com.moringa.automated_donation_platform_android.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cloudinary.Api;
import com.moringa.automated_donation_platform_android.models.Admin;
import com.moringa.automated_donation_platform_android.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminViewModel extends ViewModel {
    MutableLiveData<List<Admin>> adminAllCharities;
    MutableLiveData <Admin> approveCharity_request;
    MutableLiveData<Void> deleteCharityRequest;

    public AdminViewModel() {
        this.adminAllCharities = new MutableLiveData<>();

    }

    public MutableLiveData<List<Admin>> getAdminAllCharitiesObserve() {
        return adminAllCharities;
    }

    public MutableLiveData<Admin> getApproveCharityObserve() {
        return approveCharity_request;
    }

    public void setApproveCharity(MutableLiveData<Admin> approveCharity) {
        this.approveCharity_request = approveCharity;
    }

    public MutableLiveData<Void> getDeleteCharityRequestObserve() {
        return deleteCharityRequest;
    }

    void sendApprovalRequest(Admin charity){//send approval request
        Call<Admin> call= ApiClient.getAdminServices().sendRequestToAdmin(charity);
        call.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                if(response.isSuccessful()) {
                    System.out.println("sendApprovalRequest::: is a success");
                    approveCharity_request.postValue(response.body());
                }else System.out.println("sendApprovalRequest::: is not a success");
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {

            }
        });
    }
    void getAllAdminCharities(){//get all charities
        Call <List<Admin>> call = ApiClient.getAdminServices().adminGetAllCharities();
        call.enqueue(new Callback<List<Admin>>() {
            @Override
            public void onResponse(Call<List<Admin>> call, Response<List<Admin>> response) {
                if(response.isSuccessful()){
                    System.out.println("AdminGetAllCharities::: is a success" );
                    adminAllCharities.setValue(response.body());
                }else System.out.println("AdminGetAllCharities::: is not a success");
            }
            @Override
            public void onFailure(Call<List<Admin>> call, Throwable t) {

            }
        });
    }
    void approveAdminCharity(String charityId){ //approve a charity
        Call<Admin> call = ApiClient.getAdminServices().adminApproveCharity(charityId);
        call.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                if(response.isSuccessful()){
                    System.out.println("charityApproval::: is a success" );
                    approveCharity_request.postValue(response.body());
                }else{
                    System.out.println("charityApproval::: is not a success");
                }
            }
            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
            }
        });
    }
    void deleteRequest_charity( String charityId){
        Call<Void> call = ApiClient.getAdminServices().adminDeleteCharityOrganisation(charityId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200){
                    System.out.println("charity request::::: deleted successfully");
                }else {
                    System.out.println("charity Request::: is not a success");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


}
