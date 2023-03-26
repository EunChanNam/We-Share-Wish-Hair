package com.example.wishhair.sign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wishhair.CustomRetryPolicy;
import com.example.wishhair.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EmailCertActivity extends AppCompatActivity {

    final static private String URL_SEND = UrlConst.URL + "/api/email/send";
    final static private String URL_VALIDATE = UrlConst.URL + "/api/email/validate";

    private EditText ed_email, ed_code;
    private Button btn_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity_email_cert);

//        topBar
        Button btn_back = findViewById(R.id.toolbar_btn_back);
        btn_back.setOnClickListener(view -> finish());
        TextView pageTitle = findViewById(R.id.toolbar_textView_title);
        pageTitle.setText("");

//        send request
        ed_email = findViewById(R.id.sign_cert_et_email);
        Button btn_send = findViewById(R.id.sign_cert_btn_requestSend);
        btn_send.setOnClickListener(view -> {
            String inputEmail = String.valueOf(ed_email.getText());
            emailSendRequest(inputEmail);
        });

//        confirmCode request
        ed_code = findViewById(R.id.sign_cert_et_code);
        Button btn_submit = findViewById(R.id.sign_cert_btn_confirmCode);
        btn_submit.setOnClickListener(view -> {
            String inputCode = String.valueOf(ed_code.getText());
            emailValidateRequest(inputCode);
        });

//        intent Page
        btn_intent = findViewById(R.id.sign_cert_btn_intent);
        btn_intent.setOnClickListener(view -> {
            Intent intent = new Intent(EmailCertActivity.this, RegisterActivity.class);
            intent.putExtra("inputEmail", ed_email.getText().toString());
            startActivity(intent);
            finish();
        });
    }

    private void emailSendRequest(String inputEmail) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", inputEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_SEND, jsonObject, response -> {
            try {
                String sessionId = response.getString("sessionId");
                saveSessionId(sessionId);
                Log.d("send request response", sessionId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            String message = getErrorMessage(error);
            Log.e("send error message", message);
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);

        CustomRetryPolicy retryPolicy = new CustomRetryPolicy();
        jsonObjectRequest.setRetryPolicy(retryPolicy);

        queue.add(jsonObjectRequest);
    }

    private void saveSessionId(String sessionId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("sessionId", sessionId);
        editor.apply();
    }

    private String getSessionId() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString("sessionId", null);
    }

    private void emailValidateRequest(String inputCode){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("authKey", inputCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_VALIDATE, jsonObject, response -> {
            Log.d("validate response", response.toString());
            btn_intent.setVisibility(View.VISIBLE);
        }, error -> {
            String message = getErrorMessage(error);
            Log.e("validate error message", message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Cookie", "JSESSIONID=" + getSessionId()); // 세션 ID를 쿠키에 추가합니다.
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(jsonObjectRequest);
    }

    private String getErrorMessage(VolleyError error) {
        NetworkResponse networkResponse = error.networkResponse;
        if (networkResponse != null && networkResponse.data != null) {
            String jsonError = new String(networkResponse.data);
            try {
                JSONObject jsonObject = new JSONObject(jsonError);
                return jsonObject.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.e("getErrorMessage", "fail to get error message");
        return "null";
    }
}