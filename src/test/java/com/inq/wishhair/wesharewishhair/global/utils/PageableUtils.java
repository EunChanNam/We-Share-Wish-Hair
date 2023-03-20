package com.inq.wishhair.wesharewishhair.global.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public abstract class PageableUtils {
    public static Pageable getDefaultPageable() {
        return PageRequest.of(0, 4);
    }

    public static Pageable generatePageable(int page, int size) {
        return PageRequest.of(page, size);
    }
}
