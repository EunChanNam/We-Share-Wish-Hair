package com.inq.wishhair.wesharewishhair.web.hairstyle.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PagedHairStyleResponse<T> {

    private T result;

    private int contentSize;
}
