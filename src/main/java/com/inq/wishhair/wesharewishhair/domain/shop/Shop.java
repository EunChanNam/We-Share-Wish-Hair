package com.inq.wishhair.wesharewishhair.domain.shop;

import com.inq.wishhair.wesharewishhair.domain.shop.embedded.Address;
import com.inq.wishhair.wesharewishhair.domain.shop.enums.Score;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Score score;

    @Embedded
    private Address address;
}
