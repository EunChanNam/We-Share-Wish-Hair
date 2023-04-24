package com.inq.wishhair.wesharewishhair.review.domain.likereview.infra.query;

import com.inq.wishhair.wesharewishhair.review.domain.likereview.QLikeReview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeReviewQueryRepositoryImpl implements LikeReviewQueryRepository{

    private final JPAQueryFactory factory;

    private final QLikeReview like = new QLikeReview("l");

    @Override
    public boolean existByUserAndReview(Long userId, Long reviewId) {
        return factory
                .select(like)
                .from(like)
                .where(like.user.id.eq(userId))
                .where(like.review.id.eq(reviewId))
                .fetchFirst() != null;
    }
}
