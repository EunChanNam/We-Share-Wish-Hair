package com.example.wishhair.review.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.wishhair.R;

import me.relex.circleindicator.CircleIndicator3;

public class ReviewDetailActivity extends AppCompatActivity {

    private ViewPager2 sliderViewPager;
    private CircleIndicator3 circleIndicator;

    private String[] images = new String[] {
            "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);

        sliderViewPager = findViewById(R.id.review_detail_viewPager);
        circleIndicator = findViewById(R.id.review_detail_indicator);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(this, images));

        circleIndicator.setViewPager(sliderViewPager);

    }
}