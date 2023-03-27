package com.example.wishhair.review.write;

import com.example.wishhair.sign.UrlConst;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface MyApi {
    @Multipart
    @POST("/api/review")
    Call<ResponseBody> uploadFiles(
            @Header("Authorization") String token,
            @Part() List<MultipartBody.Part> files,
            @PartMap Map<String, RequestBody> params
    );
}

