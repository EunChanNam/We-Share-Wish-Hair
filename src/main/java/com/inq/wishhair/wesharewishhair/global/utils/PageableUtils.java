package com.inq.wishhair.wesharewishhair.global.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface PageableUtils {
    static Pageable getDefaultPageable() {
        return PageRequest.of(0, 4);
    }

    static Pageable generateSimplePageable(int size) {
        return PageRequest.of(0, size);
    }
}
