package com.inq.wishhair.wesharewishhair.global.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.inq.wishhair.wesharewishhair.global.utils.SortCondition.DATE;

public interface PageableGenerator {
    static Pageable getDefaultPageable() {
        return PageRequest.of(0, 4);
    }

    static Pageable generateSimplePageable(int size) {
        return PageRequest.of(0, size);
    }

    static Pageable generateDateDescPageable(int size) {
        return PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, DATE));
    }
}
