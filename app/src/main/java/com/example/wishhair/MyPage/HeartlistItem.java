package com.example.wishhair.MyPage;

import android.widget.ImageView;
import android.widget.TextView;

public class HeartlistItem {
    ImageView HeartlistPicture;
    TextView HeartlistGrade, HeartlistHeartcount;

    public HeartlistItem(ImageView imageView, TextView grade, TextView count) {
        HeartlistPicture = imageView;
        HeartlistGrade = grade;
        HeartlistHeartcount = count;
    }
    public HeartlistItem() {}

    public ImageView getHeartlistPicture() {
        return HeartlistPicture;
    }

    public void setHeartlistPicture(ImageView heartlistPicture) {
        HeartlistPicture = heartlistPicture;
    }

    public TextView getHeartlistHeartcount() {
        return HeartlistHeartcount;
    }

    public void setHeartlistHeartcount(TextView heartlistHeartcount) {
        HeartlistHeartcount = heartlistHeartcount;
    }

    public TextView getHeartlistGrade() {
        return HeartlistGrade;
    }

    public void setHeartlistGrade(TextView heartlistGrade) {
        HeartlistGrade = heartlistGrade;
    }
}
