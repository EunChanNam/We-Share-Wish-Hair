package com.example.wishhair.review;

import android.graphics.Bitmap;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReviewItem {

    // common
    private String contents;
    private String createdDate;
    private int likes;
    private String score;
    private List<String> photos;

    // recent
    private int profileImage;
    private String userNickName;
    private String authorReviewCount;
    private String authorAvgGrade;
    private boolean isHeart;
    private String contentImage1;
    private String contentImage2;

    private List<Bitmap> bitmapImages;

    public List<Bitmap> getBitmapImages() {
        return bitmapImages;
    }

    public void setBitmapImages(List<Bitmap> bitmapImages) {
        this.bitmapImages = bitmapImages;
    }

    public ReviewItem(int profileImage, String nickname, String authorReviewCount, String authorAvgGrade, List<Bitmap> bitmapIamge, String contents, String score, boolean isHeart, int likes, String createdDate) {
        this.profileImage = profileImage;
        this.userNickName = nickname;
        this.authorReviewCount = authorReviewCount;
        this.authorAvgGrade = authorAvgGrade;
        this.bitmapImages = bitmapIamge;
        this.contents = contents;
        this.score = score;
        this.isHeart = isHeart;
        this.likes = likes;
        this.createdDate = createdDate;
    }

    public ReviewItem() {}

    //사진이 1장일 때
    public ReviewItem(int profileImage, String nickname, String authorReviewCount, String authorAvgGrade, String contentImage, String contents, String score, boolean isHeart, int likes, String createdDate) {
        this.profileImage = profileImage;
        this.userNickName = nickname;
        this.authorReviewCount = authorReviewCount;
        this.authorAvgGrade = authorAvgGrade;
        this.contentImage1 = contentImage;
        this.contents = contents;
        this.score = score;
        this.isHeart = isHeart;
        this.likes = likes;
        this.createdDate = createdDate;
    }

    //사진이 2장 이상일 때
    public ReviewItem(int profileImage, String nickname, String authorReviewCount, String authorAvgGrade, String contentImage1, String contentImage2, String contents, String score, boolean isHeart, int likes, String createdDate) {
        this.profileImage = profileImage;
        this.userNickName = nickname;
        this.authorReviewCount = authorReviewCount;
        this.authorAvgGrade = authorAvgGrade;
        this.contentImage1 = contentImage1;
        this.contentImage2 = contentImage2;
        this.contents = contents;
        this.score = score;
        this.isHeart = isHeart;
        this.likes = likes;
        this.createdDate = createdDate;
    }

    // my
    private int hairImage;
    private String hairStyle;
    private String tags;
    private boolean isPoint;

    public ReviewItem(int hairImage, String hairStyle, String tags, String contents, String score, int likes, String createdDate, boolean isPoint) {
        this.hairImage = hairImage;
        this.hairStyle = hairStyle;
        this.tags = tags;
        this.score = score;
        this.likes = likes;
        this.contents = contents;
        this.createdDate = createdDate;
        this.isPoint = isPoint;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
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

    public String getContentImage1() {
        return contentImage1;
    }

    public void setContentImage1(String contentImage1) {
        this.contentImage1 = contentImage1;
    }

    public String getContentImage2() {
        return contentImage2;
    }

    public void setContentImage2(String contentImage2) {
        this.contentImage2 = contentImage2;
    }

    public void setContentImage1_1() {
        this.contentImage1 = photos.get(0);
    }

    public void setContentImage1_2() {
        this.contentImage1 = photos.get(1);
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
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
