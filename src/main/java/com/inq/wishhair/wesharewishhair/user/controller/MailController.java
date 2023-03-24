package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.AuthKeyRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.MailRequest;
import com.inq.wishhair.wesharewishhair.user.domain.Email;
import com.inq.wishhair.wesharewishhair.user.service.MailSendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.MAIL_INVALID_KEY;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class MailController {

    private static final String MAIL_TITLE = "We-Share-Wish-Hair 이메일 인증";
    private static final String AUTH_KEY = "KEY";

    private final MailSendService mailSendService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendAuthorizationMail(@ModelAttribute MailRequest mailRequest,
                                                      HttpServletRequest request) {

        String authKey = registerAuthKey(request);

        mailSendService.sendAuthorizationMail(mailRequest.toMailDto(MAIL_TITLE, authKey));

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate")
    public ResponseEntity<Void> authorizeKey(@ModelAttribute AuthKeyRequest authKeyRequest,
                                             HttpServletRequest request) {

        String inputKey = authKeyRequest.getAuthKey();
        HttpSession session = request.getSession(false);
        validateKey(session, inputKey);

        session.invalidate();

        return ResponseEntity.noContent().build();
    }

    private void validateKey(HttpSession session, String inputKey) {
        if (session == null) {
            throw new WishHairException(ErrorCode.MAIL_EXPIRED_KEY);
        }

        String authKey = (String) session.getAttribute(AUTH_KEY);
        if (!inputKey.equals(authKey)) {
            throw new WishHairException(MAIL_INVALID_KEY);
        }
    }

    private String registerAuthKey(HttpServletRequest request) {
        String authKey = createAuthKey();

        HttpSession session = request.getSession();
        session.setAttribute(AUTH_KEY, authKey);

        return authKey;
    }

    private String createAuthKey() {
        return String.valueOf((int) (Math.random() * 8999 + 1000));
    }
}
