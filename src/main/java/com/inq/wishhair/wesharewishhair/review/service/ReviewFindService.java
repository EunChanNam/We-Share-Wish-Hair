package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.utils.PageableUtils;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewFindRepository;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewSimpleResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    public List<ReviewSimpleResponse> findReviewOfMonth() {
        LocalDate startDate = generateStartDate();
        LocalDate endDate = generateEndDate();
        Pageable pageable = PageableUtils.generateSimplePageable(5);

        List<Review> result = reviewFindRepository.findReviewByCreatedDate(startDate, endDate, pageable);
        return toSimpleResponse(result);
    }

    private Slice<ReviewResponse> transferContentToResponse(Slice<Review> slice) {
        return slice.map(ReviewResponse::new);
    }

    private List<ReviewSimpleResponse> toSimpleResponse(List<Review> reviews) {
        return reviews.stream().map(ReviewSimpleResponse::new).toList();
    }

    private LocalDate generateStartDate() {
        LocalDateTime start = LocalDateTime.now().minusMonths(1);
        int startYear = start.getYear();
        int startMonth = start.getMonthValue();

        return LocalDate.of(startYear, startMonth, 1);
    }

    private LocalDate generateEndDate() {
        LocalDateTime end = LocalDateTime.now();
        int endYear = end.getYear();
        int endMonth = end.getMonthValue();

        return LocalDate.of(endYear, endMonth, 1).minusDays(1);
    }
}
