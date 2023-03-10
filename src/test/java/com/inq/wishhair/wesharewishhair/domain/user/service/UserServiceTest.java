package com.inq.wishhair.wesharewishhair.domain.user.service;

import com.inq.wishhair.wesharewishhair.WeShareWishHairApplication;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.repository.UserRepository;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@DisplayName("UserServiceTest - SpringBootTest")
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원가입 서비스 로직 테스트")
    void createUserTest() {

    }
}