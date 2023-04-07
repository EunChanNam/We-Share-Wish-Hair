package com.inq.wishhair.wesharewishhair.global.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.inq.wishhair.wesharewishhair.review.common.ReviewSortCondition.DATE;
import static com.inq.wishhair.wesharewishhair.review.common.ReviewSortCondition.LIKES;

public abstract class DefaultPageableUtils {
    public static Pageable getLikeDescPageable(int size) {
        return PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, LIKES));
    }

    public static Pageable getDateDescPageable(int size) {
        return PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, DATE));
    }

    public static Pageable getDefualtPageable() {
        return PageRequest.of(0, 10);
    }
}
