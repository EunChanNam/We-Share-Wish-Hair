package com.example.wishhair;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class CustomTokenHandler {
    Activity activity;
    SharedPreferences sharedPreferences;

    public CustomTokenHandler(Activity activity) {
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
    }

    public String getAccessToken() {
        String accessToken = sharedPreferences.getString("accessToken", "fail to get accessToken");
        Log.d("getAccessToken", accessToken);

        return accessToken;
    }
}
