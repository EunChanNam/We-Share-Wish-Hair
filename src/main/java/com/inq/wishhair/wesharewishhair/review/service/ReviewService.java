package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.photo.utils.PhotoStore;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.service.dto.ReviewCreateDto;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final HairStyleRepository hairStyleRepository;
    private final LikeReviewService likeReviewService;
    private final ReviewFindService reviewFindService;
    private final PhotoStore photoStore;
    private final PointService pointService;

    @Transactional
    public Long createReview(ReviewCreateDto dto) {

        List<Photo> photos = photoStore.storePhotos(dto.getFiles());
        User user = findUserById(dto.getUserId());
        HairStyle hairStyle = findHairStyleById(dto);

        Review review = generateReview(dto, photos, user, hairStyle);
        pointService.chargePoint(100, user.getId());

        return reviewRepository.save(review).getId();
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewFindService.findById(reviewId);
        validateIsWriter(userId, review);
        likeReviewService.deleteByReview(review);
        reviewRepository.delete(review);
    }

    private void validateIsWriter(Long userId, Review review) {
        if (!review.isWriter(userId)) {
            throw new WishHairException(ErrorCode.REVIEW_NOT_WRITER);
        }
    }

    private Review generateReview(ReviewCreateDto dto, List<Photo> photos, User user, HairStyle hairStyle) {
        return Review.createReview(
                user,
                dto.getContents(),
                dto.getScore(),
                photos,
                hairStyle
        );
    }

    private HairStyle findHairStyleById(ReviewCreateDto dto) {
        return hairStyleRepository.findById(dto.getHairStyleId())
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }
}
