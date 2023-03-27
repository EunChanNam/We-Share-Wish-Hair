package com.example.wishhair.review;

import android.net.Uri;

import androidx.annotation.NonNull;

public class ReviewItem {

    // common
    private String content;
    private String date;
    private int heartCount;
    private String grade;


    // recent
    private int profileImage;
    private String nickname;
    private String authorReviewCount;
    private String authorAvgGrade;
    private boolean isHeart;
    private String contentImage1;
    private String contentImage2;

    //사진이 1장일 때
    public ReviewItem(int profileImage, String nickname, String authorReviewCount, String authorAvgGrade, String contentImage, String content, String grade, boolean isHeart, int heartCount, String date) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.authorReviewCount = authorReviewCount;
        this.authorAvgGrade = authorAvgGrade;
        this.contentImage1 = contentImage;
        this.content = content;
        this.grade = grade;
        this.isHeart = isHeart;
        this.heartCount = heartCount;
        this.date = date;
    }

    //사진이 2장 이상일 때
    public ReviewItem(int profileImage, String nickname, String authorReviewCount, String authorAvgGrade, String contentImage1, String contentImage2, String content, String grade, boolean isHeart, int heartCount, String date) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.authorReviewCount = authorReviewCount;
        this.authorAvgGrade = authorAvgGrade;
        this.contentImage1 = contentImage1;
        this.contentImage2 = contentImage2;
        this.content = content;
        this.grade = grade;
        this.isHeart = isHeart;
        this.heartCount = heartCount;
        this.date = date;
    }

    // my
    private int hairImage;
    private String hairStyle;
    private String tags;
    private boolean isPoint;

    public ReviewItem(int hairImage, String hairStyle, String tags, String content, String grade, int heartCount, String date, boolean isPoint) {
        this.hairImage = hairImage;
        this.hairStyle = hairStyle;
        this.tags = tags;
        this.grade = grade;
        this.heartCount = heartCount;
        this.content = content;
        this.date = date;
        this.isPoint = isPoint;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAuthorReviewCount() {
        return authorReviewCount;
    }

    public void setAuthorReviewCount(String authorReviewCount) {
        this.authorReviewCount = authorReviewCount;
    }

    public String getAuthorAvgGrade() {
        return authorAvgGrade;
    }

    public void setAuthorAvgGrade(String authorAvgGrade) {
        this.authorAvgGrade = authorAvgGrade;
    }

    /*public String getContentImage1() {
        return contentImage1;
    }*/
    public Uri getContentImage1() {
        if(contentImage1 == null) return null;
        return Uri.parse(contentImage1);
    }
    public Uri getContentImage2() {
        if(contentImage2 == null) return null;
        return Uri.parse(contentImage2);
    }

    public void setContentImage1(String contentImage1) {
        this.contentImage1 = contentImage1;
    }

//    public String getContentImage2() {
//        return contentImage2;
//    }

    public void setContentImage2(String contentImage2) {
        this.contentImage2 = contentImage2;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean getIsHeart() {
        return isHeart;
    }

    public void setHeart(boolean isHeart) {
        this.isHeart = isHeart;
    }

    public int getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(int heartCount) {
        this.heartCount = heartCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHairImage() {
        return hairImage;
    }

    public void setHairImage(int hairImage) {
        this.hairImage = hairImage;
    }

    public String getHairStyle() {
        return hairStyle;
    }

    public void setHairStyle(String hairStyle) {
        this.hairStyle = hairStyle;
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
}
