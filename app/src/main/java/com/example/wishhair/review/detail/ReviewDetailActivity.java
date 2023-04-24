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

public class ReviewDetailActivity extends AppCompatActivity {

    private Button btn_back;

    //    content
    private TextView userNickname, hairStyleName, tags, score, likes, date, content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity_detail);

        btn_back = findViewById(R.id.toolbar_btn_back);
        btn_back.setOnClickListener(view -> finish());

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
        tags.setText(getIntent().getStringExtra("tags"));

        score = findViewById(R.id.review_detail_tv_score);
        score.setText(getIntent().getStringExtra("score"));

        likes = findViewById(R.id.review_detail_tv_likes);
        likes.setText(String.valueOf(getIntent().getIntExtra("likes", 0)));

        date = findViewById(R.id.review_detail_tv_date);
        date.setText(getIntent().getStringExtra("date"));

        content = findViewById(R.id.review_detail_tv_content);
        content.setText(getIntent().getStringExtra("content"));
        }

    public void showMenu(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();
        menu.setOnMenuItemClickListener(this::onMenuItemClick);
        inflater.inflate(R.menu.menu_review_detail, menu.getMenu());
        menu.show();
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_detail_modify:
                Log.d("menu selectd", "modify");
                return true;
            case R.id.menu_detail_delete:
                Log.d("menu selectd", "delete");
                return true;
            default:
                return false;
        }
    }
}
