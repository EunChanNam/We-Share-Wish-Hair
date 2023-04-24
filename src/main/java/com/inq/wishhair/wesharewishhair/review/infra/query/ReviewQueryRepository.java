package com.inq.wishhair.wesharewishhair.review.infra.query;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.infra.query.dto.response.ReviewQueryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewQueryRepository {

    Slice<ReviewQueryResponse> findReviewByPaging(Pageable pageable);

    Slice<ReviewQueryResponse> findReviewByLike(Long userId, Pageable pageable);

    Slice<ReviewQueryResponse> findReviewByUser(Long userId, Pageable pageable);

    List<Review> findReviewByCreatedDate();
}
