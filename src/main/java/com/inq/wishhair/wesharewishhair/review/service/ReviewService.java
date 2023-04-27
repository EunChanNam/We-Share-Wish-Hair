package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.service.HairStyleFindService;
import com.inq.wishhair.wesharewishhair.photo.domain.PhotoRepository;
import com.inq.wishhair.wesharewishhair.photo.service.PhotoService;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewCreateRequest;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReviewRepository;
import com.inq.wishhair.wesharewishhair.review.event.PointChargeEvent;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final LikeReviewRepository likeReviewRepository;
    private final ReviewFindService reviewFindService;
    private final PhotoService photoService;
    private final UserFindService userFindService;
    private final HairStyleFindService hairStyleFindService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Long createReview(ReviewCreateRequest request, Long userId) {

        List<String> photoUrls = photoService.uploadPhotos(request.getFiles());
        User user = userFindService.findByUserId(userId);
        HairStyle hairStyle = hairStyleFindService.findById(request.getHairStyleId());

        Review review = generateReview(request, photoUrls, user, hairStyle);
        eventPublisher.publishEvent(new PointChargeEvent(100, userId));

        return reviewRepository.save(review).getId();
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewFindService.findWithPhotosByUserId(reviewId);
        validateIsWriter(userId, review);
        likeReviewRepository.deleteAllByReview(reviewId);
        photoService.deletePhotosByReviewId(reviewId, review.getPhotos());
        reviewRepository.delete(review);
    }

    private void validateIsWriter(Long userId, Review review) {
        if (!review.isWriter(userId)) {
            throw new WishHairException(ErrorCode.REVIEW_NOT_WRITER);
        }
    }

    private Review generateReview(ReviewCreateRequest request, List<String> photos, User user, HairStyle hairStyle) {
        return Review.createReview(
                user,
                request.getContents(),
                request.getScore(),
                photos,
                hairStyle
        );
    }
}
