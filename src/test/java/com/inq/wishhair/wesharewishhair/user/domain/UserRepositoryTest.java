package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("UserRepository Test")
class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User userA;
    private User userB;
    private User userC;

    @BeforeEach
    void saveUsers() {
        userA = UserFixture.A.toEntity();
        userB = UserFixture.B.toEntity();
        userC = UserFixture.C.toEntity();

        userRepository.save(userA);
        userRepository.save(userB);
        userRepository.save(userC);
    }

    @Test
    @DisplayName("회원 저장 테스트")
    void userSaveTest() {
        User findUserA = userRepository.findById(userA.getId()).get();
        User findUserB = userRepository.findById(userB.getId()).get();
        User findUserC = userRepository.findById(userC.getId()).get();

        assertAll(
                () -> assertThat(userA.getPasswordValue()).isEqualTo(findUserA.getPasswordValue()),
                () -> assertThat(userB.getPasswordValue()).isEqualTo(findUserB.getPasswordValue()),
                () -> assertThat(userC.getPasswordValue()).isEqualTo(findUserC.getPasswordValue())
        );
    }

    @Test
    @DisplayName("로그인 아이디로 회원 조회 테스트")
    void findByLoginIdTest() {
        User findUserA = userRepository.findByEmail(this.userA.getEmail()).get();
        User findUserB = userRepository.findByEmail(this.userB.getEmail()).get();
        User findUserC = userRepository.findByEmail(this.userC.getEmail()).get();

        assertAll(
                () -> assertThat(findUserA.getPasswordValue()).isEqualTo(userA.getPasswordValue()),
                () -> assertThat(findUserB.getPasswordValue()).isEqualTo(userB.getPasswordValue()),
                () -> assertThat(findUserC.getPasswordValue()).isEqualTo(userC.getPasswordValue())
        );
    }
}