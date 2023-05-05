package com.example.wishhair;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FuncActivity extends AppCompatActivity {

    private ImageView settingImage, userImage1, userImage2, userImage3, userImage4;
    private int selectImageView;
    private final List<String> imagePaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func);

        Button btn_back = findViewById(R.id.func_btn_back);
        btn_back.setOnClickListener(view -> finish());

        imagePaths.add(0, null);
        imagePaths.add(1, null);
        imagePaths.add(2, null);
        imagePaths.add(3, null);

        userImage1 = findViewById(R.id.func_image_1);
        userImage1.setOnClickListener(view -> {
            setImageView(userImage1, 0);
        });

        userImage2 = findViewById(R.id.func_image_2);
        userImage2.setOnClickListener(view -> {
            setImageView(userImage2, 1);
        });

        userImage3 = findViewById(R.id.func_image_3);
        userImage3.setOnClickListener(view -> {
            setImageView(userImage3, 2);
        });

        userImage4 = findViewById(R.id.func_image_4);
        userImage4.setOnClickListener(view -> {
            setImageView(userImage4, 3);
        });


    }

    private void setImageView(ImageView imageView, int position) {
        selectImageView = position;
        settingImage = imageView;

//        기존에 이미지 지움
        imageView.setImageDrawable(null);
        imagePaths.set(selectImageView, null);

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityResult.launch(intent);
        Log.d("image List", imagePaths.toString());
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
                        imagePaths.set(selectImageView, getRealPathFromUri(uri));
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

    private String getRealPathFromUri(Uri uri)
    {
        String[] proj=  {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String url = cursor.getString(columnIndex);
        cursor.close();
        return  url;
    }
}
