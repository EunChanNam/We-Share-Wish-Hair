package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.common.testbase.ControllerTest;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import org.junit.jupiter.api.BeforeEach;

public class UserControllerTest extends ControllerTest {

    @BeforeEach
    void init() {
        User userA = UserFixture.A.toEntity();
    }
}
