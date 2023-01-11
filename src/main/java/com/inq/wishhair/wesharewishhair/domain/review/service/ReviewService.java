package com.inq.wishhair.wesharewishhair.domain.review.service;

import com.inq.wishhair.wesharewishhair.domain.photo.PhotoStore;
import com.inq.wishhair.wesharewishhair.domain.photo.entity.Photo;
import com.inq.wishhair.wesharewishhair.domain.review.Review;
import com.inq.wishhair.wesharewishhair.domain.review.repository.ReviewRepository;
import com.inq.wishhair.wesharewishhair.domain.review.service.dto.request.ReviewRequestDto;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.repository.UserRepository;
import com.inq.wishhair.wesharewishhair.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.exception.WishHairException;
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
    private final PhotoStore photoStore;

    @Transactional
    public Long createReview(ReviewRequestDto dto) {

        List<Photo> photos = photoStore.storePhotos(dto.getFiles());
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));

        Review review = Review.createReview(
                user,
                dto.getTitle(),
                dto.getContents(),
                dto.getScore(),
                photos
        );
        return reviewRepository.save(review).getId();
    }
}
