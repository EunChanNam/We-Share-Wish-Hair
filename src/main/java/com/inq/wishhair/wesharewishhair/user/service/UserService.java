package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PasswordUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.domain.*;
import com.inq.wishhair.wesharewishhair.user.service.dto.PasswordUpdateDto;
import com.inq.wishhair.wesharewishhair.user.service.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserFindService userFindService;

    @Transactional
    public Long createUser(User user) {

        validateNicknameIsNotDuplicated(user.getNickname());
        User saveUser = userRepository.save(user);

        return saveUser.getId();
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateRequest request) {
        User user = userFindService.findByUserId(userId);
        Nickname newNickname = new Nickname(request.getNickname());

        validateNicknameIsNotDuplicated(newNickname);

        user.updateNickname(newNickname);
        user.updateSex(request.getSex());
    }

    @Transactional
    public void updatePassword(Long userId, PasswordUpdateRequest request) {
        User user = userFindService.findByUserId(userId);
        Password newPassword = new Password(request.getOldPassword());

        confirmPassword(user, newPassword);
        user.updatePassword(newPassword);
    }

    private void confirmPassword(User user, Password password) {
        if (user.isNotSamePassword(password)) {
            throw new WishHairException(ErrorCode.USER_WRONG_PASSWORD);
        }
    }

    private void validateNicknameIsNotDuplicated(Nickname nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new WishHairException(ErrorCode.USER_DUPLICATED_NICKNAME);
        }
    }
}
