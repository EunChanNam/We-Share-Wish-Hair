package com.inq.wishhair.wesharewishhair.domain.user;

import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String pw;

    @Enumerated(EnumType.STRING)
    private Sex sex;
}
