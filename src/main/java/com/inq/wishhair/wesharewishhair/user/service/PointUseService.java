package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PointUseRequest;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointType;
import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointUseService {

    private static final String MAIL_TITLE = "We-Share-Wish-Hair 포인트 환급 요청";

    private PointService pointService;
    private UserFindService userFindService;
    private MailSendService mailSendService;

    @Transactional
    public void usePoint(PointUseRequest request, Long userId) {

        User user = userFindService.findByUserId(userId);
        pointService.insertPointHistory(PointType.USE, request.getDealAmount(), user);

        String contents = generateContents(request, user);
        MailDto mailDto = new MailDto(user.getEmail(), MAIL_TITLE, contents);

        mailSendService.sendMail(mailDto);
    }

    private String generateContents(PointUseRequest request, User user) {
        String contents = "사용자 이름 : " + user.getName() + "\n";
        contents += "계좌 번호 : " + request.getAccountNumber() + "\n";
        contents += "환급 포인트 : " + request.getDealAmount();
        return contents;
    }
}
