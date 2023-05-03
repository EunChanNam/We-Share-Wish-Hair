package com.example.wishhair.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wishhair.FuncActivity;
import com.example.wishhair.R;
import com.example.wishhair.sign.UrlConst;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {

    private static final String URL = UrlConst.URL + "api/";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);

//        notification
        ImageButton btn_notification;
        btn_notification = v.findViewById(R.id.home_btn_notification);

//        go analyze
        Button btn_go = v.findViewById(R.id.home_btn_go);
        btn_go.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), FuncActivity.class);
            startActivity(intent);
        });

//        HotReview
        ArrayList<HomeItems> hotReviewItems = new ArrayList<>();
        //===============================dummy data===============================
        for (int i = 0; i < 4; i++) {
            HomeItems newHotItems = new HomeItems("현정", "바니바니바니바니 당근당근 바니바니바니바니 당근당긴 바니바니바니바니 당근 당근바니바니바니바니 당근 당근바니바니바니바니 당근 당근");
            hotReviewItems.add(newHotItems);
        }

        ViewPager2 hotReviewPager = v.findViewById(R.id.home_ViewPager_review_hot);
        CircleIndicator3 hotIndicator = v.findViewById(R.id.home_circleIndicator);

        hotReviewPager.setOffscreenPageLimit(1);
        hotReviewPager.setAdapter(new HomeHotReviewAdapter(hotReviewItems));

        hotIndicator.setViewPager(hotReviewPager);

//        recommend
        ArrayList<HomeItems> recommendItems = new ArrayList<>();
        //===============================dummy data===============================
        String imageSample = "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg";
        for (int i = 0; i < 5; i++) {
            HomeItems newRecItems = new HomeItems(imageSample, "hairStyle", "876");
            recommendItems.add(newRecItems);
        }

        RecyclerView recommendRecyclerView = v.findViewById(R.id.home_recommend_recyclerView);
        HomeRecommendAdapter homeRecommendAdapter = new HomeRecommendAdapter(recommendItems, getContext());

        recommendRecyclerView.setAdapter(homeRecommendAdapter);
        recommendRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        return v;
    }

    private void homeRequest(String accessToken) {
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
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap();
                params.put("Authorization", "bearer" + accessToken);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(jsonObjectRequest);
    }
}