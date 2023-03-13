package com.example.wishhair.review;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wishhair.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ReviewFragment extends Fragment {

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ReviewPagerAdapter reviewPagerAdapter;

    final String[] tabs = new String[] {"리뷰 목록", "나의 리뷰"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review, container, false);

        tabLayout = v.findViewById(R.id.review_tabLayout);
        viewPager = v.findViewById(R.id.review_viewPager);
        viewPager.setAdapter(new ReviewPagerAdapter(getActivity()));

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(v.getContext());
                textView.setText(tabs[position]);
                tab.setCustomView(textView);
            }
        }).attach();

        return v;
    }
}