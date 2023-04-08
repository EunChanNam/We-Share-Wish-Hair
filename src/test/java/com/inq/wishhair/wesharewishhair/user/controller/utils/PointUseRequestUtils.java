package com.inq.wishhair.wesharewishhair.user.controller.utils;

import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PointUseRequest;

public abstract class PointUseRequestUtils {

    private static final String BANK_NAME = "기업은행";
    private static final String ACCOUNT_NUMBER = "12341234";

    public static PointUseRequest request(int dealAmount) {
        return new PointUseRequest(BANK_NAME, ACCOUNT_NUMBER, dealAmount);
    }
}
