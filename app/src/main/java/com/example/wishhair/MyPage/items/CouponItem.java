package com.example.wishhair.MyPage.items;

import android.widget.ImageButton;
import android.widget.TextView;

public class CouponItem {
    ImageButton CouponDetailButton;
    TextView CouponTitle, CouponExplanation, CouponConstraint, CouponDate;

    public CouponItem(String title, String explanation, String constraint, String date) {
        CouponTitle.setText(title);
        CouponExplanation.setText(explanation);
        CouponConstraint.setText(constraint);
        CouponDate.setText(date);
    }

    public CouponItem() {}

    public TextView getCouponTitle() {
        return CouponTitle;
    }

    public void setCouponTitle(TextView couponTitle) {
        CouponTitle = couponTitle;
    }

    public TextView getCouponExplanation() {
        return CouponExplanation;
    }

    public void setCouponExplanation(TextView couponExplanation) {
        CouponExplanation = couponExplanation;
    }

    public TextView getCouponConstraint() {
        return CouponConstraint;
    }

    public void setCouponConstraint(TextView couponConstraint) {
        CouponConstraint = couponConstraint;
    }

    public TextView getCouponDate() {
        return CouponDate;
    }

    public void setCouponDate(TextView couponDate) {
        CouponDate = couponDate;
    }
}
