package com.example.wishhair.review.write;

public class WriteRequestData {
    String hairStyleId;
    String rating;
    String content;

    public WriteRequestData() {}

    public String getHairStyleId() {
        return hairStyleId;
    }

    public void setHairStyleId(String hairStyleId) {
        this.hairStyleId = hairStyleId;
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
