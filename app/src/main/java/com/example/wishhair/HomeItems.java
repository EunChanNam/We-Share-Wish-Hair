package com.example.wishhair;

public class HomeItems {
//    hot review
    String username;
    String context_review;

//    TODO: set recommend item
//    recommend
    String hairImage;
    String hairStyle;

    public HomeItems(String username, String context_review) {
        this.username = username;
        this.context_review = context_review;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContext_review() {
        return context_review;
    }

    public void setContext_review(String context_review) {
        this.context_review = context_review;
    }
}
