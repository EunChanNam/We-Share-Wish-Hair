package com.inq.wishhair.wesharewishhair.domain.user;

import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sex sex;

    //=생성 메서드=//
    public static User createUser(String loginId, String pw,
                                  String name, String nickname, Sex sex) {
        User user = new User();
        user.loginId = loginId;
        user.pw = pw;
        user.name = name;
        user.nickname = nickname;
        user.sex = sex;
        return user;
    }
}
