package com.example.wishhair;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    ArrayList<HomeItems> hotReviewItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

//        HotReview
        hotReviewItems = new ArrayList<>();
        //===============================dummy data===============================
        for (int i = 0; i < 4; i++) {
            HomeItems newHotItems = new HomeItems("현정", "바니바니바니바니 당근당근 바니바니바니바니 당근당긴 바니바니바니바니 당근 당근바니바니바니바니 당근 당근바니바니바니바니 당근 당근");
            hotReviewItems.add(newHotItems);
        }

        ViewPager2 hotReviewPager = v.findViewById(R.id.home_ViewPager_review_hot);
        CircleIndicator3 hotIndicator = v.findViewById(R.id.home_circleIndicator);

        hotReviewPager.setOffscreenPageLimit(1);
        hotReviewPager.setAdapter(new HomeHotReviewAdapter(getContext(), hotReviewItems));

        hotIndicator.setViewPager(hotReviewPager);

//        recommend



        return v;
    }
}