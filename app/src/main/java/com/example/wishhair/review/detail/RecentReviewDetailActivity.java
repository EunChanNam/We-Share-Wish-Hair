package com.example.wishhair.review.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.wishhair.R;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class RecentReviewDetailActivity extends AppCompatActivity {

    private Button btn_back;

    //    content
    private TextView userNickname, hairStyleName, tags, score, likes, date, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_detail_activity_recent);

        btn_back = findViewById(R.id.toolbar_btn_back);
        btn_back.setOnClickListener(view -> finish());
        TextView title = findViewById(R.id.toolbar_textView_title);
        title.setText("");

        ViewPager2 sliderViewPager = findViewById(R.id.review_detail_viewPager);
        CircleIndicator3 circleIndicator = findViewById(R.id.review_detail_indicator);

        sliderViewPager.setOffscreenPageLimit(1);

        ArrayList<String> imageUrls = getIntent().getStringArrayListExtra("imageUrls");
        sliderViewPager.setAdapter(new ImageSliderAdapter(this, imageUrls));

        circleIndicator.setViewPager(sliderViewPager);

//        content
        userNickname = findViewById(R.id.review_detail_userNickname);
        userNickname.setText(getIntent().getStringExtra("userNickname"));

        hairStyleName = findViewById(R.id.review_detail_hairStyleName);
        hairStyleName.setText(getIntent().getStringExtra("hairStyleName"));

        tags = findViewById(R.id.review_detail_tags);
        ArrayList<String> hashTags = getIntent().getStringArrayListExtra("tags");
        StringBuilder stringTags = new StringBuilder();
        for (int i = 0; i < hashTags.size(); i++) {
            stringTags.append("#").append(hashTags.get(i)).append(" ");
        }
        tags.setText(stringTags);

        score = findViewById(R.id.review_detail_tv_score);
        score.setText(getIntent().getStringExtra("score"));

        likes = findViewById(R.id.review_detail_tv_likes);
        likes.setText(String.valueOf(getIntent().getIntExtra("likes", 0)));

        date = findViewById(R.id.review_detail_tv_date);
        date.setText(getIntent().getStringExtra("date"));

        content = findViewById(R.id.review_detail_tv_content);
        content.setText(getIntent().getStringExtra("content"));
        }

}
