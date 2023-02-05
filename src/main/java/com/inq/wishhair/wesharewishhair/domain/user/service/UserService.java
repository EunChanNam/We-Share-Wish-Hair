package com.inq.wishhair.wesharewishhair.domain.user.service;

import com.inq.wishhair.wesharewishhair.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.domain.point.repository.PointHistoryRepository;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public Long createUser(User user) {
        User saveUser = userRepository.save(user);
        PointHistory joinPointHistory = PointHistory.createJoinPointHistory(saveUser);
        pointHistoryRepository.save(joinPointHistory);

        return saveUser.getId();
    }
}
