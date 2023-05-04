package com.example.wishhair;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FuncApi {
    @Multipart
    @POST("/api/")
    Call<ResponseBody> uploadImages(
            @Header("Authorization") String token,
            @Part()List<MultipartBody.Part> files
            );
}
