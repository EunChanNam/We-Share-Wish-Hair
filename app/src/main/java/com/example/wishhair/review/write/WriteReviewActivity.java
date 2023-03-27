package com.example.wishhair.review.write;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.wishhair.CustomTokenHandler;
import com.example.wishhair.R;
import com.example.wishhair.sign.UrlConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WriteReviewActivity extends AppCompatActivity {
    private static final String TAG = "WriteReviewActivity";

    private Button btn_addPicture, btn_back, btn_submit;
    private EditText editText_content;

    private RecyclerView recyclerView;
    private WriteReviewAdapter writeReviewAdapter;

    private ArrayList<Uri> items = new ArrayList<>();

    private RatingBar ratingBar;

    private final WriteRequestData writeRequestData = new WriteRequestData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity_write);
//        accessToken
        CustomTokenHandler customTokenHandler = new CustomTokenHandler(this);
        String accessToken = customTokenHandler.getAccessToken();
//         hair Style info
//        #TODO hair style info 받아오기
        writeRequestData.setHairStyleId("10");

//        back
        btn_back = findViewById(R.id.toolbar_btn_back);
        btn_back.setOnClickListener(view -> finish());

        recyclerView = findViewById(R.id.write_review_picture_recyclerView);

//        RatingBar
        ratingBar = findViewById(R.id.write_review_ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float choice, boolean fromUser) {
                writeRequestData.setRating(choice);
                Log.d("setRating", writeRequestData.getRating());
            }
        });

//        addPicture
        btn_addPicture = findViewById(R.id.write_review_addPicture);
        btn_addPicture.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2222);
        });

//        content
        editText_content = findViewById(R.id.write_review_content);

//        submit
        btn_submit = findViewById(R.id.write_review_submit);
        btn_submit.setOnClickListener(view -> {
            Retrofit2MultipartUploader uploader = new Retrofit2MultipartUploader(getApplicationContext());
            String contents = String.valueOf(editText_content.getText());
            writeRequestData.setContent(contents);
            uploader.uploadFiles(writeRequestData.getHairStyleId(), "S3", writeRequestData.getContent(), items, accessToken);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            Toast.makeText(this, "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show();
        } else {
            if (data.getClipData() == null) { //이미지를 하나만 선택한경우
                Uri imageUri = data.getData();
                items.add(imageUri);

                writeReviewAdapter = new WriteReviewAdapter(items, getApplicationContext());
                recyclerView.setAdapter(writeReviewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
            } else { // 이미지 여러장
                ClipData clipData = data.getClipData();
//                이미지 선택 갯수 제한
//                !TODO : 이미지 여러장 나눠서 첨부하면 4장이상 들어감 >> items maxsize 설정해서 하면 될 것 같음
                if (clipData.getItemCount() > 4) {
                    Toast.makeText(this, "사진은 4장까지만 선택 가능합니다", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageURI = clipData.getItemAt(i).getUri();
                        try {
                            items.add(imageURI);
                        } catch (Exception e) {
                            Log.e(TAG, "file select error", e);
                        }
                    }
                    writeReviewAdapter = new WriteReviewAdapter(items, getApplicationContext());
                    recyclerView.setAdapter(writeReviewAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
                }
            }
        }
    }

}
