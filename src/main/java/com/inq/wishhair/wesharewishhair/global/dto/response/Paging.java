package com.inq.wishhair.wesharewishhair.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Paging {

    private int contentSize;

    private int page;

    private boolean hasNext;
}
