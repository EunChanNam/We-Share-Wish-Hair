package com.example.wishhair.MyPage;

import java.time.LocalDateTime;

public class PointHistory {
    private String pointType;
    private int dealAmount;
    private int point;
    private LocalDateTime dealDate;

    public String getPointType() {
        return pointType;
    }

    public int getDealAmount() {
        return dealAmount;
    }

    public LocalDateTime getDealDate() {
        return dealDate;
    }
}
