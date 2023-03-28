package com.example.wishhair.review.recent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecentReceivedData {
    String userNickName;
    String score;
    int likes;
    List<String> photos;
    String contents;
    String createDate;

    String hairStyleName;
    List<String> tags;

    public RecentReceivedData() {}

    public RecentReceivedData(String userNickName, String score, int likes, List<String> photos) {
        this.userNickName = userNickName;
        this.score = score;
        this.likes = likes;
        this.photos = photos;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {

        this.createDate = parseDate(createDate);
    }
}
