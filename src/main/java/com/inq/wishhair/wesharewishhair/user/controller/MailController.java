package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.user.controller.dto.request.MailRequest;
import com.inq.wishhair.wesharewishhair.user.domain.Email;
import com.inq.wishhair.wesharewishhair.user.service.MailService;
import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class MailController {

    private static final String MAIL_TITLE = "We-Share-Wish-Hair 이메일 인증";
    private static final String AUTH_KEY = "KEY";

    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendValidationMail(@ModelAttribute MailRequest mailRequest,
                                                   HttpServletRequest request) {

        //이메일 형식 검증
        Email email = new Email(mailRequest.getEmail());

        String authKey = registerAuthKey(email, request);
        MailDto mailDto = MailDto.of(email.getValue(), MAIL_TITLE, authKey);

        mailService.sendValidationMail(mailDto);

        return ResponseEntity.noContent().build();
    }

    private String registerAuthKey(Email email, HttpServletRequest request) {
        String authKet = createAuthKey();

        HttpSession session = request.getSession();
        session.setAttribute(AUTH_KEY, authKet);

        return authKet;
    }

    private String createAuthKey() {
        return String.valueOf((int) (Math.random() * 8999 + 1000));
    }
}
