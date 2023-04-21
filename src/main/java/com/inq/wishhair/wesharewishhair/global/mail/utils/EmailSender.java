package com.inq.wishhair.wesharewishhair.global.mail.utils;

import com.inq.wishhair.wesharewishhair.global.mail.dto.RefundMailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSender {

    private static final String AUTH_MAIL_TITLE = "We-Share-Wish-Hair 이메일 인증";
    private static final String REFUND_MAIL_TITLE = "We-Share-Wish-Hair 포인트 환급 요청";

    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;

    public void sendAuthMail(String address, String authKey) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(address);
        message.setSubject(AUTH_MAIL_TITLE);
        message.setText(authKey);

        mailSender.send(message);
    }

    public void sendRefundRequestMail(RefundMailDto dto) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(dto.getAddress());
        message.setSubject(REFUND_MAIL_TITLE);
        message.setText(generateContents(dto));

        mailSender.send(message);
    }

    private static String generateContents(RefundMailDto dto) {
        String contents = "사용자 이름 : " + dto.getUsername() + "\n";
        contents += "은행 명 : " + dto.getBankName() + "\n";
        contents += "계좌 번호 : " + dto.getAccountNumber() + "\n";
        contents += "환급 금액 : " + dto.getDealAmount();
        return contents;
    }
}
