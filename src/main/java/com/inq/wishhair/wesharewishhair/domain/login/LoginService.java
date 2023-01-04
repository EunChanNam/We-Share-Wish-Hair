package com.inq.wishhair.wesharewishhair.domain.login;

import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.repository.UserRepository;
import com.inq.wishhair.wesharewishhair.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.exception.WishHairException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final UserRepository userRepository;

    public UserSessionDto login(String loginId, String pw) {

        User findUser = userRepository.findByLoginId(loginId)
                .filter(user -> user.getPw().equals(pw))
                .orElseThrow(() -> new WishHairException(ErrorCode.LOGIN_FAIL));

        return new UserSessionDto(findUser);
    }
}
