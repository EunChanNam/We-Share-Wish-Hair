package com.inq.wishhair.wesharewishhair.user.controller.dto.request;

import com.inq.wishhair.wesharewishhair.user.event.RefundMailSendEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointUseRequest {

    private String bankName;

    private String accountNumber;

    private int dealAmount;

    public RefundMailSendEvent toRefundMailEvent(String userName) {
        return new RefundMailSendEvent(
                userName,
                bankName,
                accountNumber,
                dealAmount
        );
    }
}
