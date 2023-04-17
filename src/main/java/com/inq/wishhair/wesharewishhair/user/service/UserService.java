package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PasswordUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserFindService userFindService;
    private final UserValidator userValidator;

    @Transactional
    public Long createUser(User user) {

        userValidator.validateNicknameIsNotDuplicated(user.getNickname());
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

        userValidator.validateNicknameIsNotDuplicated(newNickname);

        user.updateNickname(newNickname);
        user.updateSex(request.getSex());
    }

    @Transactional
    public void updateFaceShape(Long userId, Tag faceShapeTag) {
        User user = userFindService.findByUserId(userId);

        user.updateFaceShape(new FaceShape(faceShapeTag));
    }

    @Transactional
    public void updatePassword(Long userId, PasswordUpdateRequest request) {
        User user = userFindService.findByUserId(userId);
        confirmPassword(user, new Password(request.getOldPassword()));

        user.updatePassword(new Password(request.getNewPassword()));
    }

    private void confirmPassword(User user, Password password) {
        if (user.isNotSamePassword(password)) {
            throw new WishHairException(ErrorCode.USER_WRONG_PASSWORD);
        }
    }
}
