package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewFindService {

    private final ReviewRepository reviewRepository;

    public Slice<ReviewResponse> findPagingReviews(Pageable pageable) {
        return reviewRepository.findReviewByPaging(pageable)
                .map(this::toResponse);
    }

    public List<Review> findLikingReviews(Long userId) {
        return reviewRepository.findReviewByLike(userId);
    }

    private ReviewResponse toResponse(Review review) {
        return new ReviewResponse(review);
    }
}
