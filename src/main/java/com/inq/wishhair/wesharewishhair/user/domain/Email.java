package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {
    private static final String EMAIL_PATTERN = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final Pattern EMAIL_MATCHER = Pattern.compile(EMAIL_PATTERN);

    private String email;

    public Email(String email) {
        validateEmailPattern(email);
        this.email = email;
    }

    private static void validateEmailPattern(String value) {
        if (isNotValidPattern(value)) {
            throw new WishHairException(ErrorCode.USER_INVALID_EMAIL);
        }
    }

    private static boolean isNotValidPattern(String email) {
        return !EMAIL_MATCHER.matcher(email).matches();
    }
}
