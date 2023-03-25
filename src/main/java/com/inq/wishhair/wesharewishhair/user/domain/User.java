package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistories;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Embedded
    private Email email;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sex sex;

    @Embedded
    private FaceShape faceShape;

    @Embedded
    private PointHistories pointHistories;

    //=생성 메서드=//
    @Builder
    public User(Email email, String pw, String name, String nickname, Sex sex) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.sex = sex;
        this.pointHistories = new PointHistories();
    }

    public String getEmailValue() {
        return email.getValue();
    }

    public Tag getFaceShape() {
        return faceShape.getTag();
    }

    public boolean existFaceShape() {
        return faceShape != null;
    }

    public void updateFaceShape(FaceShape faceShape) {
        this.faceShape = faceShape;
    }

    public int getAvailablePoint() {
        return pointHistories.getAvailablePoint();
    }

    public List<PointHistory> getPointHistories() {
        return pointHistories.getPointHistories();
    }
}
