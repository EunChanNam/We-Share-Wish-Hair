package com.inq.wishhair.wesharewishhair.user.event;

public record RefundMailSendEvent(
         String username,
         String bankName,
         String accountNumber,
         int dealAmount
) {
}
