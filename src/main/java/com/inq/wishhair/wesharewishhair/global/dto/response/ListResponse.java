package com.inq.wishhair.wesharewishhair.global.dto.response;

import java.util.List;

@FunctionalInterface
public interface ListResponse<T> {

    List<T> getResult();
}
