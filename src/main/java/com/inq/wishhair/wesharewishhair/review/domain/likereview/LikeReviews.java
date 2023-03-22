package com.inq.wishhair.wesharewishhair.review.domain.likereview;

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

    private int likes;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<LikeReview> likeReviews = new ArrayList<>();

    public void addLike(LikeReview likeReview) {
        likeReviews.add(likeReview);
        likes++;
    }

    public void cancelLike(Long userId) {
        likes--;
        likeReviews.removeIf(likeReview -> likeReview.getUser().getId().equals(userId));
    }

}
