package com.example.wishhair.sign;

import com.example.wishhair.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText ed_id, ed_pw, ed_name, ed_nickname;
    private Button btn_join;
    private RadioButton radioButton_man, radioButton_woman;
    private RadioGroup radioGroup_sex;
    private String select_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity_register);

        ed_id = findViewById(R.id.edID);
        ed_pw = findViewById(R.id.edPW);
        ed_name = findViewById(R.id.edNAME);
        ed_nickname = findViewById(R.id.edNICKNAME);
        radioGroup_sex = findViewById(R.id.radioGroupSEX);
        radioButton_man = findViewById(R.id.radio_sex_man);
        radioButton_woman = findViewById(R.id.radio_sex_woman);

        radioGroup_sex.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_sex_man:
                    select_sex = radioButton_man.getText().toString();
                    break;
                case R.id.radio_sex_woman:
                    select_sex = radioButton_woman.getText().toString();
                    break;
            }
        });

        btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(view -> {
            String id = ed_id.getText().toString();
            String pw = ed_pw.getText().toString();
            String name = ed_name.getText().toString();
            String nickname = ed_nickname.getText().toString();

            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };

            RegisterRequest registerRequest = new RegisterRequest(id, pw, name, nickname, "MAN", responseListener);
            registerRequest.setShouldCache(false);

            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerRequest);
        });
    }
}