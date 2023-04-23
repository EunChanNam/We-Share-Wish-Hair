package com.example.wishhair.sign;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

    final static private String URL = UrlConst.URL + "/api/login";

    private EditText login_id, login_pw;

    private SharedPreferences loginSP;
    //https://wonpaper.tistory.com/232

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity_login);
        login_id = findViewById(R.id.ed_login_id);
        login_pw = findViewById(R.id.ed_login_pw);

//       TODO 평소 테스트 편하게 넘어가기 위해 login 정보 미리 삽입해놓음
        login_id.setText("hath888@naver.com");
        login_pw.setText("1q2w3e4r!");

//        login
        Button login_loginBtn = findViewById(R.id.btn_login);
        login_loginBtn.setOnClickListener(view -> login_request());

//        register
        Button login_registerBtn = findViewById(R.id.btn_register);
        login_registerBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, TermsActivity.class);
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

        JSONObject userJsonObject = new JSONObject();
        try {
            userJsonObject.put("email", id);
            userJsonObject.put("pw", pw);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        loginSP = getSharedPreferences("UserInfo", MODE_PRIVATE);
        //임시 로그인 패스 코드
            /*Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();*/
        //서버 연동 코드
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, userJsonObject, response -> {
            Log.d("token", response.toString());
            try {
                String accessToken = response.getString("accessToken");
                String refreshToken = response.getString("refreshToken");

                SharedPreferences.Editor editor = loginSP.edit();
                editor.putString("accessToken", accessToken);
                editor.putString("refreshToken", refreshToken);
                editor.apply();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, error -> {
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
        });

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(jsonObjectRequest);

    }


}
