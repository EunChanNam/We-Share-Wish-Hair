package com.inq.wishhair.wesharewishhair.global.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public abstract class RepositoryTest {

    @PersistenceContext
    protected EntityManager em;
}
