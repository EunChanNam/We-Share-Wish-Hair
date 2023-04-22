package com.inq.wishhair.wesharewishhair.global.mail.dto;

import com.inq.wishhair.wesharewishhair.user.domain.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefundMailDto {

    private String address;
    private String username;
    private String bankName;
    private String accountNumber;
    private int dealAmount;

    public static RefundMailDto of(String address, String username, String bankName,
                                   String accountNumber, int dealAmount) {
        return new RefundMailDto(address, username, bankName, accountNumber, dealAmount);
    }
}