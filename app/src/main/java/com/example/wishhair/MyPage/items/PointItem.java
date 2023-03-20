package com.example.wishhair.MyPage.items;

import android.widget.TextView;

public class PointItem {
    TextView PointItemTitle;
    TextView PointItemNum;
    TextView PointItemDate;

    public PointItem() { }

    public TextView getPointItemTitle() {
        return PointItemTitle;
    }

    public void setPointItemTitle(TextView pointItemTitle) {
        PointItemTitle = pointItemTitle;
    }

    public TextView getPointItemNum() {
        return PointItemNum;
    }

    public void setPointItemNum(TextView pointItemNum) {
        PointItemNum = pointItemNum;
    }

    public TextView getPointItemDate() {
        return PointItemDate;
    }

    public void setPointItemDate(TextView pointItemDate) {
        PointItemDate = pointItemDate;
    }
}
