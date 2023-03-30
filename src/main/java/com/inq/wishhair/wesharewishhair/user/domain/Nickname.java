package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Nickname {

    private static final String NICKNAME_PATTERN = "^[\\w가-힣ㄱ-ㅎㅏ-ㅣ]{2,8}(\\s?[\\w가-힣ㄱ-ㅎㅏ-ㅣ]{1,7})?$";
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
