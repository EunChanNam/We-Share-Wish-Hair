package com.inq.wishhair.wesharewishhair.domain.user.repository;

import com.inq.wishhair.wesharewishhair.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);
}
