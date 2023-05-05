package com.example.wishhair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.wishhair.sign.UrlConst;
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FuncUploader {
    private final FuncApi api;
    private final Context context;

    public FuncUploader(FuncApi api, Context context) {
        //로그를 보기 위한 Interceptor
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new OkHttpProfilerInterceptor());
        }
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlConst.URL)
                .client(client)
                .build();
        this.api = retrofit.create(FuncApi.class);
        this.context = context;
    }

    public void uploadUserImages(ArrayList<String> imagePaths, String accessToken) {
        ArrayList<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < imagePaths.size(); i++) {
            File file = new File(imagePaths.get(i));
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            String fileName = "userImage" + i + ".jpg";
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("files", fileName, imageBody);

            parts.add(imagePart);
        }
        Call<ResponseBody> call = api.uploadImages("bearer" + accessToken, parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                TODO 다음 페이지 연결
                Intent intent = new Intent();
                context.startActivity(intent);
                ((Activity)context).finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Func Image Upload fail", t.toString());
            }
        });

    }
}
