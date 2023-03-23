package com.example.wishhair.MyPage.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.wishhair.MyPage.CouponList;
import com.example.wishhair.MyPage.CouponReceive;


public class CouponPagerAdapter extends FragmentStateAdapter {

    private final int list = 2;

    public CouponPagerAdapter(FragmentManager fm, Lifecycle lifecycle) {
        super(fm,lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new CouponList();
        else
            return new CouponReceive();
    }
    @Override
    public int getItemCount() {
        return list;
    }
}
