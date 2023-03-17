package com.inq.wishhair.wesharewishhair.auth.domain;

import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByUser(User user);

    @Query("select t from Token t where t.user.id = :userId " +
            "and t.refreshToken = :refreshToken")
    Optional<Token> findByUserIdAndRefreshToken(@Param("userId") Long userId,
                                                @Param("refreshToken") String refreshToken);

    @Query("delete from Token t where t.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    @Modifying // RTR 정책에 의한 RefreshToken 업데이트
    @Query("update Token SET refreshToken = :refreshToken " +
            "where user.id = :userId")
    void updateRefreshTokenByUserId(@Param("userId") Long userId,
                                    @Param("refreshToken") String refreshToken);
}
