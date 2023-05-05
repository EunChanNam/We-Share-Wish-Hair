package com.example.wishhair.sign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wishhair.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FindPasswordActivity extends AppCompatActivity {

    private TextView message;
    private EditText ed_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        message = findViewById(R.id.find_password_message2);
        ed_email = findViewById(R.id.find_password_et_email);
        String inputEmail = ed_email.getText().toString();

        Button btn_confirm = findViewById(R.id.find_password_btn_request);
        btn_confirm.setOnClickListener(view -> findPasswordRequest(inputEmail));

        Button btn_cancel = findViewById(R.id.find_password_btn_cancel);
        btn_cancel.setOnClickListener(view -> finish());
    }

    private void findPasswordRequest(String inputEmail){
        String URL = UrlConst.URL + "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", inputEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                return params;
            }
        };
    }
}
