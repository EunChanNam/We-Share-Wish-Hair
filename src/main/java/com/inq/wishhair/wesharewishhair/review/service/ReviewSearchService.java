package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.utils.PageableUtils;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewSearchRepository;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewSearchService {

    private final ReviewSearchRepository reviewSearchRepository;

    public Slice<ReviewResponse> findPagedReviews(Pageable pageable) {
        Slice<Review> sliceResult = reviewSearchRepository.findReviewByPaging(pageable);
        return transferContentToResponse(sliceResult, true);
    }

    public Slice<ReviewResponse> findLikingReviews(Long userId, Pageable pageable) {
        Slice<Review> sliceResult = reviewSearchRepository.findReviewByLike(userId, pageable);
        return transferContentToResponse(sliceResult, false);
    }

    public Slice<ReviewResponse> findMyReviews(Long userId, Pageable pageable) {
        Slice<Review> sliceResult = reviewSearchRepository.findReviewByUser(userId, pageable);

        return transferContentToResponse(sliceResult, true);
    }

    public List<ReviewSimpleResponse> findReviewOfMonth() {
        LocalDateTime startDate = generateStartDate();
        LocalDateTime endDate = generateEndDate();
        Pageable pageable = PageableUtils.generateSimplePageable(5);

        List<Review> result = reviewSearchRepository.findReviewByCreatedDate(startDate, endDate, pageable);
        return toSimpleResponse(result);
    }

    private Slice<ReviewResponse> transferContentToResponse(Slice<Review> slice, boolean isPhotoLoaded) {
        return slice.map(review -> new ReviewResponse(review, isPhotoLoaded));
    }

    private List<ReviewSimpleResponse> toSimpleResponse(List<Review> reviews) {
        return reviews.stream().map(ReviewSimpleResponse::new).toList();
    }

    private LocalDateTime generateStartDate() {
        return LocalDateTime.now().minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0);
    }

    private LocalDateTime generateEndDate() {
        return LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);
    }
}
