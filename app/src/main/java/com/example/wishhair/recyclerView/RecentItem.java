package com.example.wishhair.recyclerView;

public class RecentItem {

    // common
    private String authorAvgGrade;
    private String content;
    private String grade;
    private int heartCount;
    private String date;

    // recent
    private int profileImage;
    private String nickname;
    private String authorReviewCount;
    private boolean isHeart;
    private int contentImage1;
    private int contentImage2;

    // my
    private int hairImage;
    private String hairStyle;
    private String tags;
    private boolean isPoint;






    public RecentItem(int profileImage, String nickname, String authorReviewCount, String authorAvgGrade, int contentImage1, int contentImage2, String content, String grade, boolean isHeart, int heartCount, String date) {
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

    public int getContentImage1() {
        return contentImage1;
    }

    public void setContentImage1(int contentImage1) {
        this.contentImage1 = contentImage1;
    }

    public int getContentImage2() {
        return contentImage2;
    }

    public void setContentImage2(int contentImage2) {
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

    public void setIsHeart(boolean isHeart) {
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
}
