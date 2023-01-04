package com.example.wishhair.sign;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = UrlConst.URL + "/api/user";
    private final Map<String, String> map;

    public RegisterRequest(String loginId, String pw, String name, String nickname, String sex, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);

        map = new HashMap<>();
        map.put("loginId", loginId);
        map.put("pw", pw);
        map.put("name", name);
        map.put("nickname", nickname);
        map.put("sex", sex);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }

}
