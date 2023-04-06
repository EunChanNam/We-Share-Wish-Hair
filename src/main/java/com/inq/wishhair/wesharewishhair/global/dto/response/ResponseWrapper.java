package com.inq.wishhair.wesharewishhair.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseWrapper<T> {

    private T result;

    public static <T> ResponseWrapper<T> wrapResponse(T response) {
        return new ResponseWrapper<>(response);
    }
}
