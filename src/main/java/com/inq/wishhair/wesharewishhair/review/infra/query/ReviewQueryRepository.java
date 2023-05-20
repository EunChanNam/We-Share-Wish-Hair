package com.inq.wishhair.wesharewishhair.review.infra.query;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.infra.query.dto.response.ReviewQueryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface ReviewQueryRepository {

    //리뷰 단건 조회
    Optional<ReviewQueryResponse> findReviewById(Long id);

    //전체 리뷰 조회
    Slice<ReviewQueryResponse> findReviewByPaging(Pageable pageable);

    //좋아요 한 리뷰 조회
    Slice<ReviewQueryResponse> findReviewByLike(Long userId, Pageable pageable);

    //작성한 리뷰 조회
    Slice<ReviewQueryResponse> findReviewByUser(Long userId, Pageable pageable);

    //지난달에 작성한 리뷰 조회
    List<Review> findReviewByCreatedDate();

    //헤어스타일의 리뷰 조회
    List<ReviewQueryResponse> findReviewByHairStyle(Long hairStyleId);
}
