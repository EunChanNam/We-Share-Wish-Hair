package com.example.wishhair.review;

import android.graphics.Bitmap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReviewItem {

    // common
    private String hairStyleName;
    private String tags;
    private int likes;
    private String score;
    private String createdDate;
    private String content;

    public ReviewItem() {}

    // recent
    private ArrayList<String> imageUrls;
    private String userNickName;
    private boolean isHeart;

    //사진이 있을 때
    public ReviewItem(String userNickName,String hairStyleName, String tags, String createdDate, String score, int likes, boolean isHeart, ArrayList<String> imageUrls) {
        this.userNickName = userNickName;
        this.hairStyleName = hairStyleName;
        this.tags = tags;
        this.createdDate = createdDate;
        this.likes = likes;
        this.score = score;
        this.isHeart = isHeart;
        this.imageUrls = imageUrls;
    }

    //사진이 없을 때
    public ReviewItem(String userNickName,String hairStyleName, String tags, String createdDate, String score, int likes, boolean isHeart) {
        this.createdDate = createdDate;
        this.likes = likes;
        this.score = score;
        this.hairStyleName = hairStyleName;
        this.tags = tags;
        this.userNickName = userNickName;
        this.isHeart = isHeart;
    }

    // my
    private int hairImage;
    private boolean isPoint;

    public ReviewItem(int hairImage, String hairStyleName, String tags, String contents, String score, int likes, String createdDate, boolean isPoint) {
        this.hairImage = hairImage;
        this.hairStyleName = hairStyleName;
        this.tags = tags;
        this.score = score;
        this.likes = likes;
        this.createdDate = createdDate;
        this.isPoint = isPoint;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public boolean getIsHeart() {
        return isHeart;
    }

    public void setHeart(boolean isHeart) {
        this.isHeart = isHeart;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = parseDate(createdDate);
    }

    private String parseDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());

        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "failParseDate";
    }

    public int getHairImage() {
        return hairImage;
    }

    public void setHairImage(int hairImage) {
        this.hairImage = hairImage;
    }

    public String getHairStyleName() {
        return hairStyleName;
    }

    public void setHairStyleName(String hairStyleName) {
        this.hairStyleName = hairStyleName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean getIsPoint() {
        return isPoint;
    }

    public void setPoint(boolean point) {
        isPoint = point;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
