package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.consts.Condition;
import com.inq.wishhair.wesharewishhair.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.repository.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.likereview.LikeReview;
import com.inq.wishhair.wesharewishhair.likereview.repository.LikeReviewRepository;
import com.inq.wishhair.wesharewishhair.photo.PhotoStore;
import com.inq.wishhair.wesharewishhair.photo.entity.Photo;
import com.inq.wishhair.wesharewishhair.review.Review;
import com.inq.wishhair.wesharewishhair.review.repository.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.service.dto.ReviewCreateDto;
import com.inq.wishhair.wesharewishhair.user.User;
import com.inq.wishhair.wesharewishhair.user.repository.UserRepository;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
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

        likeReviewRepository.findByUserAndReview(user, review)
                .ifPresentOrElse(likeReviewRepository::delete, () -> {
                    LikeReview likeReview = LikeReview.createLikeReview(user, review);
                    likeReviewRepository.save(likeReview);
                });
    }

    public List<Review> getReviews(Pageable pageable, String condition) {
        List<Review> reviews = reviewRepository.findReviewByPaging(pageable);
        if (!reviews.isEmpty()) {
            reviews.get(0).getPhotos().isEmpty();
        }
        // Query 에서 정렬이 안돼서 Service 에서 정렬
        if (condition.equals(Condition.LIKES)) {
            reviews.sort((a, b) -> Integer.compare(b.getLikeReviews().size(), a.getLikeReviews().size()));
        }
        return reviews;
    }
}
