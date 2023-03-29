package com.example.wishhair.review.write;

import com.example.wishhair.BuildConfig;
import com.example.wishhair.sign.UrlConst;
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Retrofit2MultipartUploader {

    private static final String TAG = "Uploader";
    private final MyApi api;
    private final Context context;

    public Retrofit2MultipartUploader(Context context) {
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
        api = retrofit.create(MyApi.class);
        this.context = context;
    }

    public void uploadFiles(String hairStyleId, String score, String contents, ArrayList<Uri> fileUri, String accessToken) {
        Log.d("requestData", hairStyleId + " / " + score + " / " + contents);
        Log.d("requestDataPhotos", fileUri.toString());
        RequestBody hairIdBody = RequestBody.create(MediaType.parse("text/plain"), hairStyleId);
        RequestBody scoreBody = RequestBody.create(MediaType.parse("text/plain"), score);
        RequestBody contentsBody = RequestBody.create(MediaType.parse("text/plain"), contents);

        HashMap<String, RequestBody> requestMap = new HashMap<>();
        requestMap.put("hairStyleId", hairIdBody);
        requestMap.put("score", scoreBody);
        requestMap.put("contents", contentsBody);

        ArrayList<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < fileUri.size(); i++) {
            // Uri 타입의 파일경로를 가지는 RequestBody 객체 생성
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), String.valueOf(fileUri.get(i)));

            // 사진 파일 이름
            String fileName = "photo" + i + ".jpg";
            // RequestBody로 Multipart.Part 객체 생성
            MultipartBody.Part files = MultipartBody.Part.createFormData("files", fileName, fileBody);

            // 추가
            parts.add(files);
        }

        Call<ResponseBody> call = api.uploadFiles("bearer" + accessToken, parts, requestMap);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "response");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG + "fail", t.toString());
            }
        });
    }

}
