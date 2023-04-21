package com.inq.wishhair.wesharewishhair.review.infra.query;

import com.inq.wishhair.wesharewishhair.review.domain.QReview;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.QLikeReview;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.review.common.ReviewSortCondition.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository{

    private final JPAQueryFactory factory;

    private final QReview review = new QReview("r");
    private final QLikeReview like = new QLikeReview("l");

    @Override
    public Slice<Review> findReviewByPaging(Pageable pageable) {
        JPAQuery<Review> query = factory
                .select(review)
                .from(review)
                .leftJoin(review.hairStyle)
                .fetchJoin()
                .leftJoin(review.user)
                .fetchJoin();

        applyOrderBy(query, pageable);
        List<Review> result = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        return new SliceImpl<>(result, pageable, validateHasNext(pageable, result));
    }

    @Override
    public Slice<Review> findReviewByLike(Long userId, Pageable pageable) {
        JPAQuery<Review> query = factory
                .select(review)
                .from(review)
                .innerJoin(review.likeReviews.likeReviews, like)
                .on(like.user.id.eq(review.id))
                .leftJoin(review.user)
                .fetchJoin()
                .leftJoin(review.hairStyle)
                .fetchJoin()
                .orderBy(review.id.desc());
        applyPaging(query, pageable);
        List<Review> result = query.fetch();

        return new SliceImpl<>(result, pageable, validateHasNext(pageable, result));
    }

    @Override
    public Slice<Review> findReviewByUser(Long userId, Pageable pageable) {
        JPAQuery<Review> query = factory
                .select(review)
                .from(review)
                .leftJoin(review.hairStyle)
                .fetchJoin()
                .leftJoin(review.user)
                .fetchJoin()
                .where(review.id.eq(userId));
        applyOrderBy(query, pageable);
        applyPaging(query, pageable);

        List<Review> result = query.fetch();
        return new SliceImpl<>(result, pageable, validateHasNext(pageable, result));
    }

    @Override
    public List<Review> findReviewByCreatedDate(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return null;
    }

    private void applyPaging(JPAQuery<Review> query, Pageable pageable) {
        query.offset(pageable.getOffset()).limit(pageable.getPageSize());
    }

    private void applyOrderBy(JPAQuery<Review> query, Pageable pageable) {
        String sort = pageable.getSort().toString();
        switch (sort) {
            case LIKES -> query
                    .leftJoin(review.likeReviews.likeReviews)
                    .groupBy(review.id)
                    .orderBy(review.count().desc());
            case DATE_DESC -> query.orderBy(review.id.desc());
            case DATE_ASC -> query.orderBy(review.id.asc());
        }
    }

    private boolean validateHasNext(Pageable pageable, List<Review> result) {
        if (result.size() > pageable.getPageSize()) {
            result.remove(pageable.getPageSize());
            return true;
        }
        return false;
    }
}
