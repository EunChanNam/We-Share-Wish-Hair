package com.inq.wishhair.wesharewishhair.review.domain.likereview;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LikeReviews {

    private int likes;

    @OneToMany(mappedBy = "review", cascade = PERSIST, orphanRemoval = true)
    private List<LikeReview> likeReviews = new ArrayList<>();
}
