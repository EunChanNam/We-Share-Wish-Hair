package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class Nickname {

    private static final String NICKNAME_PATTERN = "^(?=.*[a-zA-Z0-9가-힣])[a-zA-Z0-9가-힣]{2,8}$";
    private static final Pattern NICKNAME_MATCHER = Pattern.compile(NICKNAME_PATTERN);

    @Column(name = "nickname", nullable = false, unique = true)
    private String value;

    public Nickname(String nickname) {
        validateNicknamePattern(nickname);
        this.value = nickname;
    }

    private void validateNicknamePattern(String nickname) {
        if (isNotValidPattern(nickname)) {
            throw new WishHairException(ErrorCode.USER_INVALID_NICKNAME);
        }
    }

    private static boolean isNotValidPattern(String nickname) {
        return !NICKNAME_MATCHER.matcher(nickname).matches();
    }
}
