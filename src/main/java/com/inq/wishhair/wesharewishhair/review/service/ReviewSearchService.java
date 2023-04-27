package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReviewRepository;
import com.inq.wishhair.wesharewishhair.review.infra.query.dto.response.ReviewQueryResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewDetailResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponseAssembler.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewSearchService {

    private final ReviewRepository reviewRepository;
    private final LikeReviewRepository likeReviewRepository;

    /*리뷰 단건 조회*/
    public ReviewDetailResponse findReviewById(Long reviewId, Long userId) {
        Review findReview = reviewRepository.findReviewById(reviewId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));


        return toReviewDetailResponse(findReview, userId);
    }

    /*전체 리뷰 조회*/
    public PagedResponse<ReviewResponse> findPagedReviews(Pageable pageable, Long userId) {
        Slice<ReviewQueryResponse> sliceResult = reviewRepository.findReviewByPaging(pageable);
        return toPagedReviewResponse(sliceResult, userId);
    }

    /*좋아요한 리뷰 조회*/
    public PagedResponse<ReviewResponse> findLikingReviews(Long userId, Pageable pageable) {
        Slice<ReviewQueryResponse> sliceResult = reviewRepository.findReviewByLike(userId, pageable);
        return toPagedReviewResponse(sliceResult, userId);
    }

    /*나의 리뷰 조회*/
    public PagedResponse<ReviewResponse> findMyReviews(Long userId, Pageable pageable) {
        Slice<ReviewQueryResponse> sliceResult = reviewRepository.findReviewByUser(userId, pageable);

        return toPagedReviewResponse(sliceResult, userId);
    }

    /*이달의 추천 리뷰 조회*/
    public ResponseWrapper<ReviewSimpleResponse> findReviewOfMonth() {
        List<Review> result = reviewRepository.findReviewByCreatedDate();
        return toWrappedSimpleResponse(result);
    }
}
