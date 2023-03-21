package com.inq.wishhair.wesharewishhair.review.service.dto.response;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.enums.Score;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
import jakarta.persistence.Persistence;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewResponse {

    private String hairStyleName;

    private String userNickName;

    private Score score;

    private List<PhotoResponse> photos;

    private int likes;

    public ReviewResponse(Review review) {
        this.hairStyleName = review.getHairStyle().getName();
        this.userNickName = review.getUser().getName();
        this.score = review.getScore();
        this.likes = review.getLikes();
        //지연로딩 처리 (batch_fetch_size)
        this.photos =  review.getPhotos().stream()
                .map(PhotoResponse::new).toList();
    }
}
