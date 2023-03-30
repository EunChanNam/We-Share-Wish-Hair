package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.domain.Nickname;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserFindRepository;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
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
    private final UserFindRepository userFindRepository;

    @Transactional
    public Long createUser(User user) {
        User saveUser = userRepository.save(user);

        return saveUser.getId();
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateDto dto) {
        User user = userFindService.findByUserId(userId);

        validateNicknameIsNotDuplicated(dto.getNickname());
        user.updateNickname(dto.getNickname());

        if (user.isNotSameSex(dto.getSex())) {
            user.updateSex(dto.getSex());
        }
    }

    private void validateNicknameIsNotDuplicated(Nickname nickname) {
        if (userFindRepository.existsByNickname(nickname)) {
            throw new WishHairException(ErrorCode.USER_DUPLICATED_NICKNAME);
        }
    }
}
