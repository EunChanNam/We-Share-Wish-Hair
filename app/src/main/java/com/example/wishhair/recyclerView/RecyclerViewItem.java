package com.example.wishhair.recyclerView;

public class RecyclerViewItem {

    private String profileImage;
    private String nickname;
    private String authorReviewCount;
    private String authorAvgGrade;
    private String contentImage1;
    private String contentImage2;
    private String content;
    private String grade;
    private String heart;

    public RecyclerViewItem() {}

    public RecyclerViewItem(String profileImage, String nickname, String authorReviewCount, String authorAvgGrade, String contentImage1, String contentImage2, String content, String grade, String heart) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.authorReviewCount = authorReviewCount;
        this.authorAvgGrade = authorAvgGrade;
        this.contentImage1 = contentImage1;
        this.contentImage2 = contentImage2;
        this.content = content;
        this.grade = grade;
        this.heart = heart;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
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

    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart;
    }
}
