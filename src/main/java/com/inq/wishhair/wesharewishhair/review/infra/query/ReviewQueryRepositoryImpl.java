package com.inq.wishhair.wesharewishhair.review.infra.query;

import com.inq.wishhair.wesharewishhair.review.domain.QReview;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.QLikeReview;
import com.inq.wishhair.wesharewishhair.review.infra.query.dto.response.QReviewQueryResponse;
import com.inq.wishhair.wesharewishhair.review.infra.query.dto.response.ReviewQueryResponse;
import com.querydsl.core.types.ConstructorExpression;
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
    public Slice<ReviewQueryResponse> findReviewByPaging(Pageable pageable) {
        JPAQuery<ReviewQueryResponse> query = factory
                .select(assembleReviewProjection())
                .from(review)
                .leftJoin(review.likeReviews.likeReviews, like)
                .leftJoin(review.hairStyle)
                .fetchJoin()
                .leftJoin(review.user)
                .fetchJoin()
                .groupBy(review.id);

        applyOrderBy(query, pageable);
        List<ReviewQueryResponse> result = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        return new SliceImpl<>(result, pageable, validateHasNext(pageable, result));
    }

    @Override
    public Slice<ReviewQueryResponse> findReviewByLike(Long userId, Pageable pageable) {
        List<ReviewQueryResponse> result = factory
                .select(assembleReviewProjection())
                .from(review)
                .innerJoin(review.likeReviews.likeReviews, like)
                .leftJoin(review.user)
                .fetchJoin()
                .leftJoin(review.hairStyle)
                .fetchJoin()
                .where(like.user.id.eq(userId))
                .groupBy(review.id)
                .orderBy(review.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        return new SliceImpl<>(result, pageable, validateHasNext(pageable, result));
    }

    @Override
    public Slice<ReviewQueryResponse> findReviewByUser(Long userId, Pageable pageable) {
        JPAQuery<ReviewQueryResponse> query = factory
                .select(assembleReviewProjection())
                .from(review)
                .leftJoin(review.likeReviews.likeReviews, like)
                .leftJoin(review.hairStyle)
                .fetchJoin()
                .leftJoin(review.user)
                .fetchJoin()
                .groupBy(review.id)
                .where(review.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L);
        applyOrderBy(query, pageable);

        List<ReviewQueryResponse> result = query.fetch();
        return new SliceImpl<>(result, pageable, validateHasNext(pageable, result));
    }

    @Override
    public List<Review> findReviewByCreatedDate() {
        LocalDateTime startDate = generateStartDate();
        LocalDateTime endDate = generateEndDate();

        return factory
                .select(review)
                .from(review)
                .leftJoin(review.hairStyle)
                .fetchJoin()
                .leftJoin(review.user)
                .fetchJoin()
                .leftJoin(review.likeReviews.likeReviews)
                .where(review.createdDate.between(startDate, endDate))
                .groupBy(review.id)
                .orderBy(review.count().desc())
                .offset(0)
                .limit(4)
                .fetch();
    }

    private ConstructorExpression<ReviewQueryResponse> assembleReviewProjection() {
        return new QReviewQueryResponse(review, review.count(), like.id.sum());
    }

    private void applyOrderBy(JPAQuery<ReviewQueryResponse> query, Pageable pageable) {
        String sort = pageable.getSort().toString().replace(": ", ".");
        switch (sort) {
            case LIKES_DESC -> query
                    .orderBy(review.count().desc());
            case DATE_DESC -> query.orderBy(review.id.desc());
            case DATE_ASC -> query.orderBy(review.id.asc());
        }
    }

    private boolean validateHasNext(Pageable pageable, List<ReviewQueryResponse> result) {
        if (result.size() > pageable.getPageSize()) {
            result.remove(pageable.getPageSize());
            return true;
        }
        return false;
    }

    private LocalDateTime generateStartDate() {
        return LocalDateTime.now().minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0);
    }

    private LocalDateTime generateEndDate() {
        return LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);
    }
}
