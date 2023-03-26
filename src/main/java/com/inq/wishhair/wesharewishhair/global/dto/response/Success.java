package com.inq.wishhair.wesharewishhair.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Success {

    private final boolean isSuccess;

    public Success() {
        this.isSuccess = true;
    }
}
