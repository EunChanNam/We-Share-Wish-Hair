package com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class WishHairs {

    @OneToMany(mappedBy = "hairStyle")
    private final List<WishHair> wishHairs = new ArrayList<>();
}
