package com.example.wishhair.MyPage;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wishhair.MainActivity;
import com.example.wishhair.MyPage.adapters.MyPageRecyclerViewAdapter;
import com.example.wishhair.MyPage.items.HeartlistItem;
import com.example.wishhair.R;
import com.example.wishhair.review.write.WriteReviewActivity;
import com.example.wishhair.sign.LoginActivity;
import com.example.wishhair.sign.UrlConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyPageFragment extends Fragment {

    MainActivity mainActivity;
    RecyclerView HeartlistRecyclerView;
    MyPageRecyclerViewAdapter adapter;
    RecyclerDecoration recyclerDecoration;

    private SharedPreferences loginSP;
    final static private String url = UrlConst.URL + "/api/logout";
    final static private String url2 = UrlConst.URL + "/api/my_page";

    static String testName = null;
    TextView tv;

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

        HeartlistRecyclerView = view.findViewById(R.id.HeartlistRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        HeartlistRecyclerView.setLayoutManager(layoutManager);
//        recyclerDecoration = new RecyclerDecoration(-200);
//        HeartlistRecyclerView.addItemDecoration(recyclerDecoration);

        adapter = new MyPageRecyclerViewAdapter();
        adapter.addItem(new HeartlistItem());
        adapter.addItem(new HeartlistItem());
        adapter.addItem(new HeartlistItem());

        HeartlistRecyclerView.setAdapter(adapter);

        toMyInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.ChangeFragment(5);
            }
        });
        toMyPointList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.ChangeFragment(7);
            }
        });
        toMyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.ChangeFragment(6);
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

//        LOGOUT
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
        myPageRequest(accessToken);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mypage_fragment, container, false);
        tv = view.findViewById(R.id.mypage_nickname);
        return view;
    }

    private void logout(String accessToken) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                logout_delete_token(loginSP);
                Intent intent = new Intent(mainActivity, LoginActivity.class);
                startActivity(intent);
                mainActivity.finish();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                logout_delete_token(loginSP);
                Intent intent = new Intent(mainActivity, LoginActivity.class);
                startActivity(intent);
                mainActivity.finish();
            }

        }) {

            @Override
            public Map<String, String> getHeaders() {
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

    public void myPageRequest(String accessToken) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url2 , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    testName = response.getString("nickname");
                    Log.i("받아온 거", testName);
                    tv.setText(testName+" 님");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap();
                params.put("Authorization", "bearer" + accessToken);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonObjectRequest);
    }

}
