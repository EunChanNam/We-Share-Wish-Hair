package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.review.infra.query.ReviewQueryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewSearchRepository extends JpaRepository<Review, Long>, ReviewQueryRepository {
}
