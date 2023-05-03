package com.example.wishhair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

public class FuncActivity extends AppCompatActivity {

    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func);

        Button btn_back = findViewById(R.id.func_btn_back);
        btn_back.setOnClickListener(view -> finish());

        imageView1 = findViewById(R.id.func_image_1);

    }
}