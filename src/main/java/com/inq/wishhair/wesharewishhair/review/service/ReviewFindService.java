package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.consts.Condition;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.photo.PhotoStore;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
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

    public List<ReviewResponse> getReviews(Pageable pageable, String condition) {
        List<Review> reviews = reviewRepository.findReviewByPaging(pageable);
        // Query 에서 정렬이 안돼서 Service 에서 정렬
        if (condition.equals(Condition.LIKES)) {
            reviews.sort((a, b) -> Integer.compare(b.getLikes(), a.getLikes()));
        }

        return toResponse(reviews);
    }

    private List<ReviewResponse> toResponse(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewResponse::new)
                .toList();
    }
}
