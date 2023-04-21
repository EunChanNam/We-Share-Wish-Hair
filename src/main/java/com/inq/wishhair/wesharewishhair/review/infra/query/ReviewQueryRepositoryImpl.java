package com.inq.wishhair.wesharewishhair.review.infra.query;

import com.inq.wishhair.wesharewishhair.review.domain.QReview;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
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

    private final QReview r = new QReview("r");

    @Override
    public Slice<Review> findReviewByPaging(Pageable pageable) {
        JPAQuery<Review> query = factory
                .select(r)
                .from(r)
                .leftJoin(r.hairStyle)
                .leftJoin(r.user);

        String sort = pageable.getSort().toString();
        switch (sort) {
            case LIKES -> query
                    .join(r.likeReviews.likeReviews)
                    .groupBy(r.id)
                    .orderBy(r.count().desc());
            case DATE_DESC -> query.orderBy(r.id.desc());
            case DATE_ASC -> query.orderBy(r.id.asc());
        }
        List<Review> result = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        return new SliceImpl<>(result, pageable, validateHasNext(pageable, result));
    }

    @Override
    public Slice<Review> findReviewByLike(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Slice<Review> findReviewByUser(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public List<Review> findReviewByCreatedDate(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return null;
    }

    private boolean validateHasNext(Pageable pageable, List<Review> result) {
        if (result.size() > pageable.getPageSize()) {
            result.remove(pageable.getPageSize());
            return true;
        }
        return false;
    }
}
