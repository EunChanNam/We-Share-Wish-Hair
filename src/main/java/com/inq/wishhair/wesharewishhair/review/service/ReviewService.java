package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.photo.PhotoStore;
import com.inq.wishhair.wesharewishhair.photo.entity.Photo;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.service.dto.ReviewCreateDto;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
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
    private final PhotoStore photoStore;

    @Transactional
    public Long createReview(ReviewCreateDto dto) {

        List<Photo> photos = photoStore.storePhotos(dto.getFiles());
        User user = findUserById(dto);
        HairStyle hairStyle = findHairStyleById(dto);

        Review review = Review.createReview(
                user,
                dto.getContents(),
                dto.getScore(),
                photos,
                hairStyle
        );
        return reviewRepository.save(review).getId();
    }

    private HairStyle findHairStyleById(ReviewCreateDto dto) {
        return hairStyleRepository.findById(dto.getHairStyleId())
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }

    private User findUserById(ReviewCreateDto dto) {
        return userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }
}
