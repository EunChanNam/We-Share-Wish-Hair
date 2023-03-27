package com.example.wishhair.review.write;

public class WriteRequestData {
    String rating;
    String content;

    public WriteRequestData() {
    }

    public WriteRequestData(String rating, String content) {
        this.rating = rating;
        this.content = content;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = String.valueOf(rating);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
