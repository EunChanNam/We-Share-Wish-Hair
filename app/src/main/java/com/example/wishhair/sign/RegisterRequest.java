package com.example.wishhair.sign;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "https://0a49-210-103-3-198.jp.ngrok.io/api/user";
    private Map<String, String> map;

    public RegisterRequest(String loginId, String pw, String name, String nickname, String sex, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("loginId", loginId);
        map.put("pw", pw);
        map.put("name", name);
        map.put("nickname", nickname);
        map.put("sex", sex);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
