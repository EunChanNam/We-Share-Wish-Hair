package com.inq.wishhair.wesharewishhair.common.base;

import com.inq.wishhair.wesharewishhair.WeShareWishHairApplication;
import com.inq.wishhair.wesharewishhair.common.testrepository.PointHistoryTestRepository;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.repository.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.domain.login.LoginService;
import com.inq.wishhair.wesharewishhair.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = {WeShareWishHairApplication.class, PointHistoryTestRepository.class})
@Transactional
public abstract class ServiceTest {

    @Autowired
    protected UserService userService;

    @Autowired
    protected PointHistoryTestRepository pointHistoryTestRepository;

    @Autowired
    protected LoginService loginService;

    @Autowired
    protected HairStyleRepository hairStyleRepository;
}
