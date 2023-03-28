package com.inq.wishhair.wesharewishhair.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleResponseWrapper<T> {

    private T result;
}
