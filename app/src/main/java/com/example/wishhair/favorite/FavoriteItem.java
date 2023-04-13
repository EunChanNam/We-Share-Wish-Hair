package com.example.wishhair.favorite;

import android.widget.ImageView;
import android.widget.TextView;

public class FavoriteItem {
    ImageView FavoritePicture;
    String FavoriteGrade, FavoriteHeartcount, FavoriteStyleName;

//    public FavoriteItem(ImageView imageView, TextView grade, TextView count) {
//        FavoritePicture = imageView;
//        FavoriteGrade = grade;
//        FavoriteHeartcount = count;
//    }
    public FavoriteItem() {}

    public ImageView getFavoritePicture() {
        return FavoritePicture;
    }

    public void setFavoritePicture(ImageView FavoritePicture) {
        FavoritePicture = FavoritePicture;
    }

    public String getFavoriteStyleName() {
        return FavoriteStyleName;
    }

    public void setFavoriteStyleName(String favoriteStyleName) {
        FavoriteStyleName = favoriteStyleName;
    }

    public String getFavoriteHeartcount() {
        return FavoriteHeartcount;
    }

    public void setFavoriteHeartcount(String FavoriteHeartcount) {
        FavoriteHeartcount = FavoriteHeartcount;
    }

    public String getFavoriteGrade() {
        return FavoriteGrade;
    }

    public void setFavoriteGrade(String FavoriteGrade) {
        FavoriteGrade = FavoriteGrade;
    }
}
