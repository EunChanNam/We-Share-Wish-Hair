package com.inq.wishhair.wesharewishhair.domain.review.service;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.repository.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.domain.likereview.LikeReview;
import com.inq.wishhair.wesharewishhair.domain.likereview.repository.LikeReviewRepository;
import com.inq.wishhair.wesharewishhair.domain.photo.PhotoStore;
import com.inq.wishhair.wesharewishhair.domain.photo.entity.Photo;
import com.inq.wishhair.wesharewishhair.domain.review.Review;
import com.inq.wishhair.wesharewishhair.domain.review.repository.ReviewRepository;
import com.inq.wishhair.wesharewishhair.domain.review.service.dto.ReviewCreateDto;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.repository.UserRepository;
import com.inq.wishhair.wesharewishhair.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.exception.WishHairException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    private final PhotoStore photoStore;
    private final LikeReviewRepository likeReviewRepository;

    @Transactional
    public Long createReview(ReviewCreateDto dto) {

        List<Photo> photos = photoStore.storePhotos(dto.getFiles());
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
        HairStyle hairStyle = hairStyleRepository.findById(dto.getHairStyleId())
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));

        Review review = Review.createReview(
                user,
                dto.getTitle(),
                dto.getContents(),
                dto.getScore(),
                photos,
                hairStyle
        );
        return reviewRepository.save(review).getId();
    }

    @Transactional
    public void LikeReview(Long reviewId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));

        LikeReview likeReview = LikeReview.createLikeReview(user, review);
        likeReviewRepository.save(likeReview);
    }

    public List<Review> getReviews(Pageable pageable) {
        List<Review> reviews = reviewRepository.findReviewByPaging(pageable);
        if (!reviews.isEmpty()) {
            reviews.get(0).getLikeReviews().isEmpty();
        }
        return reviews;
    }
}
