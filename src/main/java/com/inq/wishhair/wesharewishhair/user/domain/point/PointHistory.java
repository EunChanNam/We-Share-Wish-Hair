package com.inq.wishhair.wesharewishhair.user.domain.point;

import com.inq.wishhair.wesharewishhair.auditing.BaseEntity;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PointType pointType;

    @Column(nullable = false, updatable = false)
    private int dealAmount;

    @Column(nullable = false, updatable = false)
    private int point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    //==생성 메서드==//
    private PointHistory(User user, PointType pointType, int dealAmount, int point) {
        this.pointType = pointType;
        this.dealAmount = dealAmount;
        this.point = point;
        this.user = user;
        this.createdDate = LocalDateTime.now();
    }

    public static PointHistory createPointHistory(User user, PointType pointType, int dealAmount, int point) {
        return new PointHistory(user, pointType, dealAmount, point);
    }
}
