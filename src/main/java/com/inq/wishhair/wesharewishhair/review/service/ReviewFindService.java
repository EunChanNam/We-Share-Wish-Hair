package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewFindRepository;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewFindService {

    private final ReviewFindRepository reviewFindRepository;

    public Slice<ReviewResponse> findPagingReviews(Pageable pageable) {
        Slice<Review> sliceResult = reviewFindRepository.findReviewByPaging(pageable);
        return transferContentToResponse(sliceResult);
    }

    public Slice<ReviewResponse> findLikingReviews(Long userId, Pageable pageable) {
        Slice<Review> sliceResult = reviewFindRepository.findReviewByLike(userId, pageable);
        return transferContentToResponse(sliceResult);
    }

    public Slice<ReviewResponse> findMyReviews(Long userId, Pageable pageable) {
        Slice<Review> sliceResult = reviewFindRepository.findReviewByUser(userId, pageable);

        return transferContentToResponse(sliceResult);
    }

    private Slice<ReviewResponse> transferContentToResponse(Slice<Review> slice) {
        return slice.map(ReviewResponse::new);
    }
}
