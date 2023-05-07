package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewFindService {

    private final ReviewRepository reviewRepository;

    public Review findWithPhotosById(Long id) {
        return reviewRepository.findWithPhotosById(id)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }

    public List<Review> findWithPhotosByUserId(Long userId) {
        return reviewRepository.findWithPhotosByUserId(userId);
    }
}
