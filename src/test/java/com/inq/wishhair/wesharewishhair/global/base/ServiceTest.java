package com.inq.wishhair.wesharewishhair.global.base;

import com.inq.wishhair.wesharewishhair.WeShareWishHairApplication;
import com.inq.wishhair.wesharewishhair.global.testrepository.PointHistoryTestRepository;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.user.service.UserService;
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
    protected HairStyleRepository hairStyleRepository;
}
