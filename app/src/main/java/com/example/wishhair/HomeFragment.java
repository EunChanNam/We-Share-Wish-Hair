package com.example.wishhair;

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

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {

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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

//        notification
        ImageButton btn_notification;
        btn_notification = v.findViewById(R.id.home_btn_notification);

//        go analyze
        Button btn_go = v.findViewById(R.id.home_btn_go);


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
        String image = "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg";
        for (int i = 0; i < 5; i++) {
            HomeItems newRecItems = new HomeItems(image, "hairStyle", "876");
            recommendItems.add(newRecItems);
        }

        RecyclerView recommendRecyclerView = v.findViewById(R.id.home_recommend_recyclerView);
        HomeRecommendAdapter homeRecommendAdapter = new HomeRecommendAdapter(recommendItems, getContext());

        recommendRecyclerView.setAdapter(homeRecommendAdapter);
        recommendRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));


        return v;
    }
}