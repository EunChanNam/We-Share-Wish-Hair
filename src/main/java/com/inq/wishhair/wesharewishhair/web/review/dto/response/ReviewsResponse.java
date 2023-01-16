package com.inq.wishhair.wesharewishhair.web.review.dto.response;

import com.inq.wishhair.wesharewishhair.domain.review.Review;
import com.inq.wishhair.wesharewishhair.domain.review.enums.Score;
import com.inq.wishhair.wesharewishhair.web.photo.dto.response.PhotoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewsResponse {

    private String title;

    private String hairStyleName;

    private String userNickName;

    private Score score;

    private PhotoResponse photo;

    private Integer likes;

    public ReviewsResponse(Review review) {
        this.title = review.getTitle();
        this.hairStyleName = review.getHairStyle().getName();
        this.userNickName = review.getUser().getName();
        this.score = review.getScore();
        if (!review.getPhotos().isEmpty()) {
            this.photo = new PhotoResponse(review.getPhotos().get(0));
        }
        this.likes = review.getLikeReviews().size();
    }
}
