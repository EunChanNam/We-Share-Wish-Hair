package com.inq.wishhair.wesharewishhair.domain.user.service;

import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long createUser(User user) {
        User saveUser = userRepository.save(user);
        return saveUser.getId();
    }
}
