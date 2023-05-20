package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.aop.annotation.AddisWriter;
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
    @AddisWriter
    public ReviewDetailResponse findReviewById(Long userId, Long reviewId) {
        ReviewQueryResponse queryResponse = reviewRepository.findReviewById(reviewId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));

        boolean isLiking = likeReviewRepository.existsByUserIdAndReviewId(userId, reviewId);

        return toReviewDetailResponse(queryResponse, isLiking);
    }

    /*전체 리뷰 조회*/
    @AddisWriter
    public PagedResponse<ReviewResponse> findPagedReviews(Long userId, Pageable pageable) {
        Slice<ReviewQueryResponse> sliceResult = reviewRepository.findReviewByPaging(pageable);
        return toPagedReviewResponse(sliceResult);
    }

    /*좋아요한 리뷰 조회*/
    @AddisWriter
    public PagedResponse<ReviewResponse> findLikingReviews(Long userId, Pageable pageable) {
        Slice<ReviewQueryResponse> sliceResult = reviewRepository.findReviewByLike(userId, pageable);
        return toPagedReviewResponse(sliceResult);
    }

    /*나의 리뷰 조회*/
    @AddisWriter
    public PagedResponse<ReviewResponse> findMyReviews(Long userId, Pageable pageable) {
        Slice<ReviewQueryResponse> sliceResult = reviewRepository.findReviewByUser(userId, pageable);

        return toPagedReviewResponse(sliceResult);
    }

    /*이달의 추천 리뷰 조회*/
    public ResponseWrapper<ReviewSimpleResponse> findReviewOfMonth() {
        List<Review> result = reviewRepository.findReviewByCreatedDate();
        return toWrappedSimpleResponse(result);
    }

    /*헤어스타일의 리뷰 조회*/
    @AddisWriter
    public ResponseWrapper<ReviewResponse> findReviewByHairStyle(Long userId, Long hairStyleId) {
        List<ReviewQueryResponse> result = reviewRepository.findReviewByHairStyle(hairStyleId);
        return toWrappedReviewResponse(result);
    }
}
