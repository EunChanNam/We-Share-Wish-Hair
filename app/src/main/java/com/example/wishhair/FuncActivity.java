package com.example.wishhair;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FuncActivity extends AppCompatActivity {

    private ImageView settingImage, userImage1, userImage2, userImage3, userImage4;
    private List<Uri> userImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func);

        Button btn_back = findViewById(R.id.func_btn_back);
        btn_back.setOnClickListener(view -> finish());

        userImage1 = findViewById(R.id.func_image_1);
        userImage1.setOnClickListener(view -> {
            settingImage = userImage1;
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityResult.launch(intent);
        });

        userImage2 = findViewById(R.id.func_image_2);
        userImage2.setOnClickListener(view -> {
            settingImage = userImage2;
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityResult.launch(intent);
        });

        userImage3 = findViewById(R.id.func_image_3);
        userImage3.setOnClickListener(view -> {
            settingImage = userImage3;
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityResult.launch(intent);
        });

        userImage4 = findViewById(R.id.func_image_4);
        userImage4.setOnClickListener(view -> {
            settingImage = userImage4;
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityResult.launch(intent);
        });

    }
// TODO 사진 크기별로 이상하게 들어감
//      사진 넣었을 때 배경이 남아서 못생겨짐
//      이미지 uri list 에 넣어서 서버로 보내야함
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        userImages.add(uri);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            settingImage.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
}
