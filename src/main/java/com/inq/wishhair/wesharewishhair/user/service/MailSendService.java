package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendService {

    private final JavaMailSender mailSender;
    private final UserValidator userValidator;

    public void sendMail(MailDto dto) {

        userValidator.validateEmailIsNotDuplicated(dto.getEmail());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("namhm23@kyonggi.ac.kr");
        message.setTo(dto.getEmail().getValue());
        message.setSubject(dto.getTitle());
        message.setText(dto.getContent());

        mailSender.send(message);
    }
}
