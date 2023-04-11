package com.inq.wishhair.wesharewishhair.user.event;

import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PointUseRequest;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;

public class RefundMailSendEvent extends MailSendEvent {

    private static final String MAIL_TITLE = "We-Share-Wish-Hair 포인트 환급 요청";

    public RefundMailSendEvent(PointUseRequest request, User user) {
        super(MailDto.of(user.getEmail(), MAIL_TITLE, generateContents(request, user)));
    }

    private static String generateContents(PointUseRequest request, User user) {
        String contents = "사용자 이름 : " + user.getName() + "\n";
        contents += "은행 명 : " + request.getBankName() + "\n";
        contents += "계좌 번호 : " + request.getAccountNumber() + "\n";
        contents += "환급 금액 : " + request.getDealAmount();
        return contents;
    }
}
