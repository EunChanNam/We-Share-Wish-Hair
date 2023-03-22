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
        Slice<Review> sliceResult = reviewRepository.findReviewByPaging(pageable);
        return transferContentToResponse(sliceResult);
    }

    public List<ReviewResponse> findLikingReviews(Long userId, Pageable pageable) {
        List<Review> findReviews = reviewRepository.findReviewByLike(userId, pageable);
        return generateResponses(findReviews);
    }

    private List<ReviewResponse> generateResponses(List<Review> reviews) {
        return reviews.stream().map(ReviewResponse::new).toList();
    }

    private Slice<ReviewResponse> transferContentToResponse(Slice<Review> slice) {
        return slice.map(ReviewResponse::new);
    }
}
