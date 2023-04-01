package com.inq.wishhair.wesharewishhair.user.controller.utils;

import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PointUseRequest;

public abstract class PointUseRequestUtils {

    public static PointUseRequest createRequestByDealAmount(int dealAmount) {
        return new PointUseRequest("기업 은행", "12341234", dealAmount);
    }
}
