package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PointUseRequest;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointRepository;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointType;
import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private static final String MAIL_TITLE = "We-Share-Wish-Hair 포인트 환급 요청";

    private final PointRepository pointRepository;
    private final UserFindService userFindService;
    private final MailSendService mailSendService;

    @Transactional
    public void usePoint(PointUseRequest request, Long userId) {

        User user = userFindService.findByUserId(userId);
        insertPointHistory(PointType.USE, request.getDealAmount(), user);

        sendRefundRequestMail(request, user);
    }

    @Transactional
    public void chargePoint(int dealAmount, Long userId) {

        User user = userFindService.findByUserId(userId);
        insertPointHistory(PointType.CHARGE, dealAmount, user);
    }

    private void insertPointHistory(PointType pointType, int dealAmount, User user) {

        user.updateAvailablePoint(pointType, dealAmount);
        PointHistory pointHistory = generatePointHistory(pointType, dealAmount, user);

        pointRepository.save(pointHistory);
    }

    private PointHistory generatePointHistory(PointType pointType, int dealAmount, User user) {
        int point;
        if (pointType.isCharge()) {
            point = dealAmount + user.getAvailablePoint();
        } else {
            point = user.getAvailablePoint() - dealAmount;
        }
        return PointHistory.createPointHistory(user, pointType, dealAmount, point);
    }

    private void sendRefundRequestMail(PointUseRequest request, User user) {
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

