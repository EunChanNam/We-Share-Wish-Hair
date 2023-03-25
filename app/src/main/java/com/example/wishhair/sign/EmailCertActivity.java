package com.example.wishhair.sign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private Button btn_back, btn_send, btn_submit, btn_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity_email_cert);

//        topBar
        btn_back = findViewById(R.id.toolbar_btn_back);
        btn_back.setOnClickListener(view -> finish());
        TextView pageTitle = findViewById(R.id.toolbar_textView_title);
        pageTitle.setText("");

//        send request
        ed_email = findViewById(R.id.sign_cert_et_email);
        btn_send = findViewById(R.id.sign_cert_btn_requestSend);
        btn_send.setOnClickListener(view -> {
            String inputEmail = String.valueOf(ed_email.getText());
            emailSendRequest(inputEmail);
        });

//        confirmCode request
        ed_code = findViewById(R.id.sign_cert_et_code);
        btn_submit = findViewById(R.id.sign_cert_btn_confirmCode);
        btn_submit.setOnClickListener(view -> {
            String inputCode = String.valueOf(ed_code.getText());
            emailValidateRequest(inputCode);
        });

//        intent Page
        btn_intent = findViewById(R.id.sign_cert_btn_intent);
        btn_intent.setOnClickListener(view -> {
            Intent intent = new Intent(EmailCertActivity.this, RegisterActivity.class);
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
                Log.d("send request response", response.getString("success"));
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

    private void emailValidateRequest(String inputCode){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("authKey", inputCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_VALIDATE, jsonObject, response -> {
            Log.d("validate response", response.toString());
        }, error -> {
            String message = getErrorMessage(error);
            Log.e("validate error message", message);
        });

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