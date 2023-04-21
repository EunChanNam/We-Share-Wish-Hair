package com.inq.wishhair.wesharewishhair.user.controller.dto.request;

import com.inq.wishhair.wesharewishhair.global.mail.dto.RefundMailDto;
import com.inq.wishhair.wesharewishhair.user.domain.User;
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

    public RefundMailDto refundMailDto(User user) {
        return RefundMailDto.of(user.getEmailValue(),
                user.getName(),
                bankName,
                accountNumber,
                dealAmount);
    }
}
