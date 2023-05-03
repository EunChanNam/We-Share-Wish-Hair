package com.example.wishhair.MyPage;

import java.time.LocalDateTime;

public class PointHistory {
    private String pointType;
    private int dealAmount;
    private int point;
    private String dealDate;

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public void setDealAmount(int dealAmount) {
        this.dealAmount = dealAmount;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public String getPointType() {
        return pointType;
    }

    public int getDealAmount() {
        return dealAmount;
    }

    public String getDealDate() {
        return dealDate;
    }
}
