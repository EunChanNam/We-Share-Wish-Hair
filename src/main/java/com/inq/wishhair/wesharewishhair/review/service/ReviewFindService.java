package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReviewFindService {

    private final ReviewRepository reviewRepository;

    public Review findWithPhotosByUserId(Long id) {
        return reviewRepository.findWithPhotosById(id)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }

    public Review findWithLikeReviewsById(Long id) {
        return reviewRepository.findWithLikesById(id)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }
}
