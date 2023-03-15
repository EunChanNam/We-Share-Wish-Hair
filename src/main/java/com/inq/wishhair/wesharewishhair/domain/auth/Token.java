package com.inq.wishhair.wesharewishhair.domain.auth;

import com.inq.wishhair.wesharewishhair.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, unique = true)
    private String refreshToken;


    //생성 메서드
    private Token(User user, String refreshToken) {
        this.user = user;
        this.refreshToken = refreshToken;
    }

    public static Token issueRefreshToken(User user, String refreshToken) {
        return new Token(user, refreshToken);
    }

    //편의 메서드
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
