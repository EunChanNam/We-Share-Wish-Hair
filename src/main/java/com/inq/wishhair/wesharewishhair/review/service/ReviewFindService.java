package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewFindService {

    private final ReviewRepository reviewRepository;

    public List<ReviewResponse> getReviews(Pageable pageable) {
        List<Review> reviews = reviewRepository.findReviewByPaging(pageable);

        return toResponse(reviews);
    }

    private List<ReviewResponse> toResponse(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewResponse::new)
                .toList();
    }
}
