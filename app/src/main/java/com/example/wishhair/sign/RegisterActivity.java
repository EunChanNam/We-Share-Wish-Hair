package com.example.wishhair.sign;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wishhair.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    final static private String URL = UrlConst.URL + "/api/user";

    private EditText ed_pw, ed_name, ed_nickname;
    private RadioButton radioButton_man, radioButton_woman;
    private String select_sex;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity_register);

        Button btn_back = findViewById(R.id.botBar_btn_back);
        btn_back.setOnClickListener(view -> finish());

        ed_name = findViewById(R.id.sign_register_et_name);
        ed_nickname = findViewById(R.id.sign_register_et_nickname);
        ed_pw = findViewById(R.id.sign_register_et_password);

        RadioGroup radioGroup_sex = findViewById(R.id.radioGroupSEX);
        radioButton_man = findViewById(R.id.radio_sex_man);
        radioButton_woman = findViewById(R.id.radio_sex_woman);

        radioGroup_sex.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_sex_man:
                    select_sex = "MAN";
                    break;
                case R.id.radio_sex_woman:
                    select_sex = "WOMAN";
                    break;
            }
        });

//        Button btn_id_dup_check = findViewById(R.id.btn_id_dup_check);

        Button btn_join = findViewById(R.id.botBar_btn_next);
        btn_join.setOnClickListener(view -> registerRequest(select_sex));
    }

    private void registerRequest(String select_sex) {
        String id = getIntent().getStringExtra("inputEmail");
        String pw = ed_pw.getText().toString();
        String name = ed_name.getText().toString();
        String nickname = ed_nickname.getText().toString();

        JSONObject userJsonObject = new JSONObject();
        try {
            userJsonObject.put("email", id);
            userJsonObject.put("pw", pw);
            userJsonObject.put("name", name);
            userJsonObject.put("nickname", nickname);
            userJsonObject.put("sex", select_sex);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, userJsonObject, response -> {
            Log.d("register success", response.toString());
            Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }, error -> {
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null && networkResponse.data != null) {
                String jsonError = new String(networkResponse.data);
                try {
                    JSONObject jsonObject = new JSONObject(jsonError);
                    String message = jsonObject.getString("message");
                    Log.e("register error", message);
                    Toast.makeText(RegisterActivity.this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("Error", "Unknown error occurred.");
            }
        });

        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(jsonObjectRequest);
    }
}
