package com.inq.wishhair.wesharewishhair.domain.shop.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String roadAddr;
}
