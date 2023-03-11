package com.example.wishhair.recyclerView;

public class RecyclerViewItem {

    private int profileImage;
    private String nickname;
    private String authorReviewCount;
    private String authorAvgGrade;
    private int contentImage1;
    private int contentImage2;
    private String content;
    private String grade;
    private boolean isHeart;
    private int heartCount;
    private String date;

    public RecyclerViewItem() {}

    public RecyclerViewItem(int profileImage, String nickname, String authorReviewCount, String authorAvgGrade, int contentImage1, int contentImage2, String content, String grade,boolean isHeart, int heartCount, String date) {
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
