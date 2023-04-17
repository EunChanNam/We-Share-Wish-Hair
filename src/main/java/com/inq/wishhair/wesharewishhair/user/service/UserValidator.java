package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.domain.Email;
import com.inq.wishhair.wesharewishhair.user.domain.Nickname;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateNicknameIsNotDuplicated(Nickname nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new WishHairException(ErrorCode.USER_DUPLICATED_NICKNAME);
        }
    }

    public void validateEmailIsNotDuplicated(Email email) {
        if (userRepository.existsByEmail(email)) {
            throw new WishHairException(ErrorCode.USER_DUPLICATED_EMAIL);
        }
    }
}
