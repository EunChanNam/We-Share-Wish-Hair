package com.inq.wishhair.wesharewishhair.global.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.inq.wishhair.wesharewishhair.review.common.ReviewSortCondition.DATE;
import static com.inq.wishhair.wesharewishhair.review.common.ReviewSortCondition.LIKES;

public abstract class DefaultPageableUtils {
    public static Pageable getLikeDescPageable() {
        return PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, LIKES));
    }

    public static Pageable getDateDescPageable() {
        return PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, DATE));
    }
}
