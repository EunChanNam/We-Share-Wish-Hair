package com.inq.wishhair.wesharewishhair.domain.user.repository;

import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@DisplayName("UserRepository Test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 저장 테스트")
    void userSaveTest() {
        User userA = UserFixture.A.toEntity();
        User userB = UserFixture.B.toEntity();
        User userC = UserFixture.C.toEntity();

        userRepository.save(userA);
        userRepository.save(userB);
        userRepository.save(userC);

        User findUserA = userRepository.findById(userA.getId()).get();
        User findUserB = userRepository.findById(userB.getId()).get();
        User findUserC = userRepository.findById(userC.getId()).get();

        assertAll(
                () -> assertThat(userA.getPw()).isEqualTo(findUserA.getPw()),
                () -> assertThat(userB.getPw()).isEqualTo(findUserB.getPw()),
                () -> assertThat(userC.getPw()).isEqualTo(findUserC.getPw())
        );
    }


}