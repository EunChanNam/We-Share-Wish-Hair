package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("UserRepository Test")
class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFindRepository userFindRepository;

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
                () -> assertThat(userA.getPassword()).isEqualTo(findUserA.getPassword()),
                () -> assertThat(userB.getPassword()).isEqualTo(findUserB.getPassword()),
                () -> assertThat(userC.getPassword()).isEqualTo(findUserC.getPassword())
        );
    }

    @Test
    @DisplayName("로그인 아이디로 회원 조회 테스트")
    void findByLoginIdTest() {
        User findUserA = userFindRepository.findByEmail(this.userA.getEmail()).get();
        User findUserB = userFindRepository.findByEmail(this.userB.getEmail()).get();
        User findUserC = userFindRepository.findByEmail(this.userC.getEmail()).get();

        assertAll(
                () -> assertThat(findUserA.getPassword()).isEqualTo(findUserA.getPassword()),
                () -> assertThat(findUserB.getPassword()).isEqualTo(findUserB.getPassword()),
                () -> assertThat(userC.getPassword()).isEqualTo(findUserC.getPassword())
        );
    }
}