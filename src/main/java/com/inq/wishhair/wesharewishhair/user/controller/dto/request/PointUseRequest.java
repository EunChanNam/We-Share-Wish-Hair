package com.inq.wishhair.wesharewishhair.user.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointUseRequest {

    private String accountNumber;

    private int dealAmount;
}
