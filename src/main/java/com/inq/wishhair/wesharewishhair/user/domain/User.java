package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.Tag;
import com.inq.wishhair.wesharewishhair.user.domain.point.Points;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointType;

import javax.persistence.*;
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

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Nickname nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sex sex;

    @Embedded
    private FaceShape faceShape;

    @Embedded
    private Points points;

    //=생성 메서드=//
    private User(Email email, Password password, String name, Nickname nickname, Sex sex) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.sex = sex;
        this.faceShape = new FaceShape();
        this.points = new Points();
    }

    public static User createUser(String email, Password password, String name, String nickname, Sex sex) {
        return new User(new Email(email), password, name, new Nickname(nickname), sex);
    }

    public String getEmailValue() {
        return email.getValue();
    }

    public Tag getFaceShapeTag() {
        return faceShape.getTag();
    }

    public boolean existFaceShape() {
        if (faceShape == null) return false;
        return faceShape.getTag() != null;
    }

    public void updateFaceShape(FaceShape faceShape) {
        this.faceShape = faceShape;
    }

    public int getPoints() {
        return points.getAvailablePoint();
    }

    public void updateAvailablePoint(PointType pointType, int dealAmount) {
        points.updateAvailablePoint(pointType, dealAmount, this);
    }

    public void updatePassword(Password password) {
        this.password = password;
    }

    public void updateNickname(Nickname nickname) {
        this.nickname = nickname;
    }

    public void updateSex(Sex sex) {
        this.sex = sex;
    }

    public String getPasswordValue() {
        return password.getValue();
    }

    public String getNicknameValue() {
        return nickname.getValue();
    }
}
