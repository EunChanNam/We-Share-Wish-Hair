package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.auth.domain.TokenRepository;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.review.service.ReviewService;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PasswordRefreshRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PasswordUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.SignUpRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserFindService userFindService;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final ReviewService reviewService;
    private final TokenRepository tokenRepository;

    @Transactional
    public Long createUser(SignUpRequest request) {

        User user = generateUser(request);
        userValidator.validateNicknameIsNotDuplicated(user.getNickname());

        User saveUser = userRepository.save(user);

        return saveUser.getId();
    }

    @Transactional
    public void deleteUser(Long userId) {
        tokenRepository.deleteByUserId(userId);
        reviewService.deleteReviewByWriter(userId);
        userRepository.deleteById(userId);
    }

    @Transactional
    public void refreshPassword(PasswordRefreshRequest request, Long userId) {
        User user = userFindService.findByUserId(userId);

        user.updatePassword(Password.encrypt(request.getNewPassword(), passwordEncoder));
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
        confirmPassword(user, request.getOldPassword());

        user.updatePassword(Password.encrypt(request.getNewPassword(), passwordEncoder));
    }

    private User generateUser(SignUpRequest request) {
        return User.createUser(
                request.getEmail(),
                Password.encrypt(request.getPw(), passwordEncoder),
                request.getName(),
                request.getNickname(),
                request.getSex());
    }

    private void confirmPassword(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPasswordValue())) {
            throw new WishHairException(ErrorCode.USER_WRONG_PASSWORD);
        }
    }
}
