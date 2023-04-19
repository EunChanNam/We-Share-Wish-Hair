package com.inq.wishhair.wesharewishhair.global.base;

import com.inq.wishhair.wesharewishhair.global.config.JpaAuditingConfig;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JpaAuditingConfig.class
))
public abstract class RepositoryTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected HairStyleRepository hairStyleRepository;

    @PersistenceContext
    protected EntityManager em;
}
