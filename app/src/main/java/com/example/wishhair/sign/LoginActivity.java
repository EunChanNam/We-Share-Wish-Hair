package com.example.wishhair.sign;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.wishhair.MainActivity;
import com.example.wishhair.R;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wishhair.review.write.WriteReviewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    private EditText login_id, login_pw;

    private SharedPreferences loginSP;
    //https://wonpaper.tistory.com/232

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity_login);
        login_id = findViewById(R.id.ed_login_id);
        login_pw = findViewById(R.id.ed_login_pw);

//        login
        Button login_loginBtn = findViewById(R.id.btn_login);
        login_loginBtn.setOnClickListener(view -> login_request());

//        register
        Button login_registerBtn = findViewById(R.id.btn_register);
        login_registerBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, EmailCertActivity.class);
            LoginActivity.this.startActivity(intent);
        });

//        find passwd
//        TODO 0316 : 비밀번호 찾기 구현 여부
        Button btn_findPassword = findViewById(R.id.btn_findPassword);
        btn_findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, WriteReviewActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login_request() {
        String id = login_id.getText().toString();
        String pw = login_pw.getText().toString();

        loginSP = getSharedPreferences("UserInfo", MODE_PRIVATE);
        //임시 로그인 패스 코드
            /*Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();*/
        //서버 연동 코드
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                token
                Log.d("token", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String accessToken = jsonObject.getString("accessToken");
                    String refreshToken = jsonObject.getString("refreshToken");

                    SharedPreferences.Editor editor = loginSP.edit();
                    editor.putString("accessToken", accessToken);
                    editor.putString("refreshToken", refreshToken);
                    editor.apply();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        };

        Response.ErrorListener errorResponse = error -> {
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null && networkResponse.data != null) {
                String jsonError = new String(networkResponse.data);
                try {
                    JSONObject jsonObject = new JSONObject(jsonError);
                    String message = jsonObject.getString("message");
                    Toast.makeText( getApplicationContext(), message, Toast.LENGTH_SHORT ).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                login_pw.setText("");
            }
        };

        LoginRequest loginRequest = new LoginRequest(id, pw, responseListener, errorResponse);
        loginRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);

//        Log.i("login Request", loginRequest.toString());
//        Log.i("login Request", queue.toString());

    }


}
