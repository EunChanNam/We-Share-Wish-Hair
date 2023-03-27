package com.inq.wishhair.wesharewishhair.global.base;

import com.inq.wishhair.wesharewishhair.WeShareWishHairApplication;
import com.inq.wishhair.wesharewishhair.auth.domain.TokenRepository;
import com.inq.wishhair.wesharewishhair.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.global.testrepository.PointHistoryTestRepository;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import com.inq.wishhair.wesharewishhair.user.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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

    @Autowired
    protected TokenRepository tokenRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected JwtTokenProvider provider;

    @PersistenceContext
    protected EntityManager em;
}
