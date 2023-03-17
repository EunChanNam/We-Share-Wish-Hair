package com.inq.wishhair.wesharewishhair.domain.likereview;

import com.inq.wishhair.wesharewishhair.domain.review.Review;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    //==생성 메서드==//
    public static LikeReview createLikeReview(User user, Review review) {
        LikeReview likeReview = new LikeReview();
        likeReview.user = user;
        likeReview.review = review;
        review.getLikeReviews().add(likeReview);
        return likeReview;
    }
}
