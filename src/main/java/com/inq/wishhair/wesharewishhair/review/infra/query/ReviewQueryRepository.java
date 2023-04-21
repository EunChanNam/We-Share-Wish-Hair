package com.inq.wishhair.wesharewishhair.review.infra.query;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewQueryRepository {

    Slice<Review> findReviewByPaging(Pageable pageable);

    Slice<Review> findReviewByLike(Long userId, Pageable pageable);

    Slice<Review> findReviewByUser(Long userId, Pageable pageable);

    List<Review> findReviewByCreatedDate(LocalDateTime startDate,
                                         LocalDateTime endDate,
                                         Pageable pageable);
}
