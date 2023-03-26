package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.global.dto.response.Success;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.AuthKeyRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.MailRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.response.SessionIdResponse;
import com.inq.wishhair.wesharewishhair.user.service.MailSendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.MAIL_INVALID_KEY;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class MailController {

    private static final String MAIL_TITLE = "We-Share-Wish-Hair 이메일 인증";
    private static final String AUTH_KEY = "KEY";

    private final MailSendService mailSendService;

    @PostMapping("/send")
    public ResponseEntity<SessionIdResponse> sendAuthorizationMail(@RequestBody MailRequest mailRequest,
                                                                   HttpServletRequest request) {

        String authKey = createAuthKey();
        String sessionId = registerAuthKey(request, authKey);

        mailSendService.sendAuthorizationMail(mailRequest.toMailDto(MAIL_TITLE, authKey));

        return ResponseEntity.ok(new SessionIdResponse(sessionId));
    }

    @PostMapping("/validate")
    public ResponseEntity<Success> authorizeKey(@RequestBody AuthKeyRequest authKeyRequest,
                                             HttpServletRequest request) {

        String inputKey = authKeyRequest.getAuthKey();
        HttpSession session = request.getSession(false);
        validateKey(session, inputKey);

        session.invalidate();

        return ResponseEntity.ok(new Success());
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

    private String registerAuthKey(HttpServletRequest request, String authKey) {
        HttpSession session = request.getSession();
        session.setAttribute(AUTH_KEY, authKey);

        return session.getId();
    }

    private String createAuthKey() {
        return String.valueOf((int) (Math.random() * 8999 + 1000));
    }
}
