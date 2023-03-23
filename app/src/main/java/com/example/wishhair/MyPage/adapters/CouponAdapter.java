package com.example.wishhair.MyPage.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishhair.MyPage.items.CouponItem;
import com.example.wishhair.R;

import java.util.ArrayList;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder>{
    private ArrayList<CouponItem> couponItems = new ArrayList<CouponItem>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton CouponItemDetailButton;
        public TextView CouponItemTitle, CouponItemExplanation, CouponItemConstraint, CouponItemDate;

        ViewHolder(View view) {
            super(view);

            CouponItemDetailButton = view.findViewById(R.id.coupon_detail_button);
            CouponItemTitle = view.findViewById(R.id.coupon_title);
            CouponItemExplanation = view.findViewById(R.id.coupon_explanation);
            CouponItemConstraint = view.findViewById(R.id.coupon_constraint);
            CouponItemDate = view.findViewById(R.id.coupon_date);
        }
    }

    @NonNull
    @Override
    public CouponAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.coupon_item, parent, false);
        return new CouponAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CouponItem item = couponItems.get(position);
    }

    @Override
    public int getItemCount() {
        return couponItems.size();
    }

    public void addItem(CouponItem e) {
        couponItems.add(e);
    }
}
