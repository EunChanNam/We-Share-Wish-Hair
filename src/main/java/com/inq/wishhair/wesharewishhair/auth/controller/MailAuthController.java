package com.inq.wishhair.wesharewishhair.auth.controller;

import com.inq.wishhair.wesharewishhair.global.dto.response.Success;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.auth.controller.dto.request.AuthKeyRequest;
import com.inq.wishhair.wesharewishhair.auth.controller.dto.request.MailRequest;
import com.inq.wishhair.wesharewishhair.auth.event.AuthMailSendEvent;
import com.inq.wishhair.wesharewishhair.user.domain.Email;
import com.inq.wishhair.wesharewishhair.user.service.UserValidator;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.SessionIdResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.MAIL_INVALID_KEY;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class MailAuthController {

    private static final String AUTH_KEY = "KEY";

    private final ApplicationEventPublisher eventPublisher;

    private final UserValidator userValidator;

    @PostMapping("/check")
    public ResponseEntity<Success> checkDuplicateEmail(@RequestBody MailRequest request) {

        userValidator.validateEmailIsNotDuplicated(new Email(request.getEmail()));

        return ResponseEntity.ok(new Success());
    }

    @PostMapping("/send")
    public ResponseEntity<SessionIdResponse> sendAuthorizationMail(@RequestBody MailRequest mailRequest,
                                                                   HttpServletRequest request) throws NoSuchAlgorithmException {

        String authKey = createAuthKey();
        String sessionId = registerAuthKey(request, authKey);

        eventPublisher.publishEvent(new AuthMailSendEvent(mailRequest.getEmail(), authKey));

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

    private String createAuthKey() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        return String.valueOf(random.nextInt(8999) + 1000);
    }
}
