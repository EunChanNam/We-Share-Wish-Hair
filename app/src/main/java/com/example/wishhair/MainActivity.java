package com.example.wishhair;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wishhair.MyPage.InformationModifyFragment;
import com.example.wishhair.MyPage.MyCouponFragment;
import com.example.wishhair.MyPage.MyInformationFragment;
import com.example.wishhair.MyPage.MyPageFragment;
import com.example.wishhair.MyPage.MyPointList;
import com.example.wishhair.MyPage.MySelectionlistFragment;
import com.example.wishhair.MyPage.MyStyleFragment;

import com.example.wishhair.review.ReviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final HomeFragment homeFragment = new HomeFragment();
    private final RecommendFragment recommendFragment = new RecommendFragment();
    private final ReviewFragment reviewFragment = new ReviewFragment();
    private final MyPageFragment myPageFragment = new MyPageFragment();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainLayout, homeFragment).commitAllowingStateLoss();

        BottomNavigationView BottomNavigation = findViewById(R.id.BottomNavigation);
        BottomNavigation.setOnItemSelectedListener(new BottomNavigationItemSelectedListener());

    }

    class BottomNavigationItemSelectedListener implements NavigationBarView.OnItemSelectedListener{

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(item.getItemId())
            {
                case R.id.bot_nav_home:
                    transaction.replace(R.id.MainLayout, homeFragment).commitAllowingStateLoss();
                    break;
                case R.id.bot_nav_recommend:
                    transaction.replace(R.id.MainLayout, recommendFragment).commitAllowingStateLoss();
                    break;
                case R.id.bot_nav_review:
                    transaction.replace(R.id.MainLayout, reviewFragment).commitAllowingStateLoss();
                    break;
                case R.id.bot_nav_myPage:
                    transaction.replace(R.id.MainLayout, myPageFragment).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}