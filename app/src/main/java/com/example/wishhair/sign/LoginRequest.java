package com.example.wishhair.sign;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL = UrlConst.URL + "/api/login";
    private final Map<String, String> map;

    public LoginRequest(String loginId, String pw, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);

        map = new HashMap<>();
        map.put("loginId", loginId);
        map.put("pw", pw);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
