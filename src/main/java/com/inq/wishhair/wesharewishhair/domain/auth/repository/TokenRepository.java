package com.inq.wishhair.wesharewishhair.domain.auth.repository;

import com.inq.wishhair.wesharewishhair.domain.auth.Token;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("select t from Token t where t.user.id = :userId")
    Optional<Token> findByUserId(@Param("userId") Long userId);

    Optional<Token> findByUserAndRefreshToken(User user, String refreshToken);

    @Query("select t from Token t where t.user.id = :userId " +
            "and t.refreshToken = :refreshToken")
    Optional<Token> findByUserIdAndRefreshToken(@Param("userId") Long userId,
                                                @Param("refreshToken") String refreshToken);

    @Query("delete from Token t where t.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
