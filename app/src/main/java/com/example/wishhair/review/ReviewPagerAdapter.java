package com.example.wishhair.review;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.wishhair.review.ReviewListFragment;
import com.example.wishhair.review.ReviewMyFragment;

public class ReviewPagerAdapter extends FragmentStateAdapter {
    private final int mPageCount = 2;

    public ReviewPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        //this.mPageCount=pageCount;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ReviewListFragment();
            case 1:
                return new ReviewMyFragment();
            default:
                return null;
        }

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
    @Override
    public int getItemCount() {
        return mPageCount;
    }
}
