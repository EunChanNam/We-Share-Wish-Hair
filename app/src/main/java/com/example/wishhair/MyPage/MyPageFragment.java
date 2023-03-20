package com.example.wishhair.MyPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wishhair.MainActivity;
import com.example.wishhair.R;
import com.example.wishhair.sign.UrlConst;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyPageFragment extends Fragment {


    MainActivity mainActivity;
    private SharedPreferences loginSP;

    public MyPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        Button toMyInformationButton = view.findViewById(R.id.toMyInformation);
        Button toMyPointList = view.findViewById(R.id.toMyPointList);
        Button toMyCoupon = view.findViewById(R.id.toMyCoupon);

        toMyInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.ChangeFragment(5);
            }
        });
        toMyPointList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.ChangeFragment(6);
            }
        });
        toMyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.ChangeFragment(7);
            }
        });


/*      HomeFragment로 이동하는 버튼 <불필요 시 삭제>
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.MainLayout, homeFragment);
                fragmentTransaction.commit();
            }
        });
        toolbar.inflateMenu(R.menu.메뉴.xml);   버튼 추가 시 사용할 것 */
    }

    final static private String url = UrlConst.URL + "/api/logout";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        Button btn_logout  = view.findViewById(R.id.mypage_button_logout);

        loginSP = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String accessToken = loginSP.getString("accessToken", "fail acc");

        Log.d("acc", loginSP.getString("accessToken", "fail acc"));
        Log.d("ref", loginSP.getString("refreshToken", "fail ref"));

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(accessToken);
            }
        });

        return view;
    }

    private void logout(String accessToken) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                logout_delete_token(loginSP);

//                TODO : 현재 액티비티(프래그먼트)를 끝내고 LoginActivity로 이동
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                logout_delete_token(loginSP);

//                TODO : 현재 액티비티(프래그먼트)를 끝내고 LoginActivity로 이동
            }

        }) {

            @Override
            public Map getHeaders() {
                Map<String, String>  params = new HashMap();
                params.put("Authorization", "bearer" + accessToken);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(jsonObjectRequest);
    }

    private void logout_delete_token (SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("accessToken");
        editor.remove("refreshToken");
        editor.apply();
    }


}