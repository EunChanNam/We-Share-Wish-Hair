package com.example.wishhair.MyPage.items;

import android.widget.ImageView;
import android.widget.TextView;

public class HeartlistItem {
    ImageView HeartlistPicture;
    String HeartlistGrade, HeartlistHeartcount;
    String HeartlistStyleName;

//    public HeartlistItem(ImageView imageView, TextView grade, TextView count, TextView StyleName) {
//        HeartlistPicture = imageView;
//        HeartlistGrade = grade;
//        HeartlistHeartcount = count;
//        HeartlistStyleName = StyleName;
//    }
    public HeartlistItem() {}

    public ImageView getHeartlistPicture() {
        return HeartlistPicture;
    }

    public void setHeartlistPicture(ImageView heartlistPicture) {
        HeartlistPicture = heartlistPicture;
    }

    public String getHeartlistHeartcount() {
        return HeartlistHeartcount;
    }

    public void setHeartlistHeartcount(String heartlistHeartcount) {
        HeartlistHeartcount = heartlistHeartcount;
    }

    public String getHeartlistGrade() {
        return HeartlistGrade;
    }

    public void setHeartlistGrade(String heartlistGrade) {
        HeartlistGrade = heartlistGrade;
    }

    public void setHeartlistStyleName(String s) {
        HeartlistStyleName = s;
    }

    public String getHeartlistStyleName() {
        return HeartlistStyleName;
    }
}
