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
                () -> assertThat(userA.getPw()).isEqualTo(findUserA.getPw()),
                () -> assertThat(userB.getPw()).isEqualTo(findUserB.getPw()),
                () -> assertThat(userC.getPw()).isEqualTo(findUserC.getPw())
        );
    }

    @Test
    @DisplayName("로그인 아이디로 회원 조회 테스트")
    void findByLoginIdTest() {
        User findUserA = userFindRepository.findByEmail(this.userA.getEmail()).get();
        User findUserB = userFindRepository.findByEmail(this.userB.getEmail()).get();
        User findUserC = userFindRepository.findByEmail(this.userC.getEmail()).get();

        assertAll(
                () -> assertThat(findUserA.getPw()).isEqualTo(findUserA.getPw()),
                () -> assertThat(findUserB.getPw()).isEqualTo(findUserB.getPw()),
                () -> assertThat(userC.getPw()).isEqualTo(findUserC.getPw())
        );
    }
}