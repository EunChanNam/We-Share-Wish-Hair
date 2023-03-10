package com.inq.wishhair.wesharewishhair.domain.user.repository;

import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveTest() {
        User userA = User.builder()
                .loginId("testA")
                .pw("test@")
                .nickname("userA")
                .name("userA")
                .sex(Sex.MAN)
                .build();

        userRepository.save(userA);
    }
}