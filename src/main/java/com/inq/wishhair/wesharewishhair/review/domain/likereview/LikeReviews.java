package com.inq.wishhair.wesharewishhair.review.domain.likereview;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LikeReviews {

    private int likes = 0;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<LikeReview> likeReviews = new ArrayList<>();

    public void executeLike(User user, Review review) {
        Long userId = user.getId();
        Long reviewId = review.getId();
        likeReviews.stream()
                .filter(likeReview -> likeReview.isSameLikeReview(userId, reviewId))
                .findAny()
                .ifPresentOrElse(this::cancelLike, () -> {
                    LikeReview likeReview = createLikeReview(user, review);
                    addLike(likeReview);
                });
    }

    private LikeReview createLikeReview(User user, Review review) {
        return LikeReview.createLikeReview(user, review);
    }

    private void addLike(LikeReview likeReview) {
        likeReviews.add(likeReview);
        likes++;
    }

    private void cancelLike(LikeReview likeReview) {
        likeReviews.remove(likeReview);
        likes--;
    }
}
