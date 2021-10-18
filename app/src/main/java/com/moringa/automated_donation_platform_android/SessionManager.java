package com.moringa.automated_donation_platform_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;

import java.util.HashMap;

import retrofit2.http.PUT;

public class SessionManager {
    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;

    public static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_ID = "id";

    public SessionManager(Context context) {
        this.context = context;
        this.usersSession = context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE);
        this.editor = usersSession.edit();
    }

    public void createLoginSession(String name,String email, String phoneNumber, String category, String image, String id){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PHONENUMBER, phoneNumber);
        editor.putString(KEY_CATEGORY,category);
        editor.putString(KEY_IMAGE,image);
        editor.putString(KEY_ID,id);

        editor.commit();
    }

    public HashMap<String,String> getUserDetailsFromSession(){
        HashMap<String,String> userData = new HashMap<>();

        userData.put(KEY_NAME,usersSession.getString(KEY_NAME,null));
        userData.put(KEY_EMAIL,usersSession.getString(KEY_EMAIL,null));
        userData.put(KEY_PHONENUMBER,usersSession.getString(KEY_PHONENUMBER,null));
        userData.put(KEY_CATEGORY,usersSession.getString(KEY_CATEGORY,null));
        userData.put(KEY_IMAGE,usersSession.getString(KEY_IMAGE,null));
        userData.put(KEY_ID,usersSession.getString(KEY_ID,null));

        return userData;
    }

    public boolean checkLogin(){
        if(usersSession.getBoolean(IS_LOGIN,false)){
            return true;
        }else{
            return false;
        }
    }

    public void logUserFromSession(){
        editor.clear();
        editor.commit();
    }
}
