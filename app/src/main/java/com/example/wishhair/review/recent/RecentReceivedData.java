package com.example.wishhair.review.recent;

import java.util.List;

public class RecentReceivedData {
    String userNickName;
    String score;
    int likes;
    List<String> photos;

    String hairStyleName;
    List<String> tags;

    public RecentReceivedData() {}

    public RecentReceivedData(String userNickName, String score, int likes, List<String> photos) {
        this.userNickName = userNickName;
        this.score = score;
        this.likes = likes;
        this.photos = photos;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
