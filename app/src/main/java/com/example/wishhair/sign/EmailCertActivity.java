package com.example.wishhair.sign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wishhair.MainActivity;
import com.example.wishhair.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EmailCertActivity extends AppCompatActivity {

    final static private String URL = UrlConst.URL + "/api/";
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

//        input email
        ed_email = findViewById(R.id.sign_cert_et_email);
        String inputEmail = String.valueOf(ed_email.getText());
//        send request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("", inputEmail);
                return super.getHeaders();
            }
        };

        btn_send = findViewById(R.id.sign_cert_btn_requestSend);

//        confirmCode request

        btn_submit = findViewById(R.id.sign_cert_btn_confirmCode);

//        intent Page
        btn_intent = findViewById(R.id.sign_cert_btn_intent);
        btn_intent.setOnClickListener(view -> {
            Intent intent = new Intent(EmailCertActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}