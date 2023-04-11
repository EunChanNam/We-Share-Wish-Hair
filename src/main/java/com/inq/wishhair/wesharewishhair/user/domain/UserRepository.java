package com.inq.wishhair.wesharewishhair.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(Email email);

    boolean existsByNickname(Nickname nickname);

    boolean existsByEmail(Email email);
}
