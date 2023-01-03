package com.inq.wishhair.wesharewishhair.domain.user.repository;

import com.inq.wishhair.wesharewishhair.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Long, User> {
}
