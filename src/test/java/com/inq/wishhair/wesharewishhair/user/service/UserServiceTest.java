package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.auth.domain.Token;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.Tag;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReview;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.*;
import com.inq.wishhair.wesharewishhair.user.controller.utils.SignUpRequestUtils;
import com.inq.wishhair.wesharewishhair.user.controller.utils.UserUpdateRequestUtils;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointType;
import com.inq.wishhair.wesharewishhair.user.utils.AiConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.inq.wishhair.wesharewishhair.global.fixture.UserFixture.*;
import static com.inq.wishhair.wesharewishhair.global.utils.MockMultipartFileUtils.createEmptyMultipartFile;
import static com.inq.wishhair.wesharewishhair.global.utils.MockMultipartFileUtils.createMultipartFile;
import static com.inq.wishhair.wesharewishhair.user.controller.utils.PasswordUpdateRequestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("UserServiceTest - SpringBootTest")
class UserServiceTest extends ServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AiConnector connector;

    @Nested
    @DisplayName("회원가입 서비스 테스트")
    class createUser {
        @Test
        @DisplayName("회원가입에 성공한다")
        void success() {
            //given
            SignUpRequest request = SignUpRequestUtils.successRequest();

            //when
            Long result = userService.createUser(request);

            //then
            Optional<User> actual = userRepository.findById(result);
            assertAll(
                    () -> assertThat(actual).isPresent(),
                    () -> {
                        User user = actual.orElseThrow();
                        assertThat(user.getName()).isEqualTo(request.getName());
                        assertThat(user.getPoints()).isZero();
                        assertThat(user.getSex()).isEqualTo(request.getSex());
                        assertThat(user.getEmailValue()).isEqualTo(request.getEmail());
                        assertThat(passwordEncoder.matches(request.getPw(), user.getPasswordValue())).isTrue();
                        assertThat(user.getNicknameValue()).isEqualTo(request.getNickname());
                    }
            );
        }

        @Test
        @DisplayName("중복된 닉네임으로 실패한다")
        void failByDuplicatedNickname() {
            //given
            SignUpRequest request = SignUpRequestUtils.successRequest();
            userRepository.save(A.toEntity());

            //when, then
            assertThatThrownBy(() -> userService.createUser(request))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.USER_DUPLICATED_NICKNAME.getMessage());
        }
    }

    @Nested
    @DisplayName("회원 탈퇴 서비스")
    class deleteUser {
        @Test
        @DisplayName("회원 탈퇴에 성공한다")
        void success() {
            //given
            User user = userRepository.save(B.toEntity());
            tokenRepository.save(Token.issue(user, "token"));
            Review review = reviewRepository.save(ReviewFixture.E.toEntity(user, null));
            reviewRepository.save(ReviewFixture.D.toEntity(user, null));
            likeReviewRepository.save(LikeReview.addLike(user.getId(), review.getId()));
            likeReviewRepository.save(LikeReview.addLike(user.getId(), review.getId()));
            user.updateAvailablePoint(PointType.CHARGE, 1000);

            //when
            userService.deleteUser(user.getId());

            //then
            List<User> userResult = userRepository.findAll();
            List<Token> tokenResult = tokenRepository.findAll();
            List<Review> reviewResult = reviewRepository.findAll();
            List<LikeReview> likeResult = likeReviewRepository.findAll();

            assertAll(
                    () -> assertThat(userResult).isEmpty(),
                    () -> assertThat(tokenResult).isEmpty(),
                    () -> assertThat(reviewResult).isEmpty(),
                    () -> assertThat(likeResult).isEmpty()
            );
        }
    }

    @Nested
    @DisplayName("회원 정보 수정 서비스")
    class updateUser {

        private User user;

        @BeforeEach
        void setUp() {
            //given
            user = userRepository.save(B.toEntity());
        }

        @Test
        @DisplayName("회원의 닉네임과 성별을 수정에 성공한다")
        void success() {
            //given
            UserUpdateRequest request = UserUpdateRequestUtils.request(A);

            //when
            userService.updateUser(user.getId(), request);

            //then
            assertAll(
                    () -> assertThat(user.getNicknameValue()).isEqualTo(request.getNickname()),
                    () -> assertThat(user.getSex()).isEqualTo(request.getSex())
            );

        }

        @Test
        @DisplayName("중복된 닉네임으로 수정에 실패한다")
        void failByDuplicatedNickname() {
            //given
            UserUpdateRequest request = UserUpdateRequestUtils.request(B);

            //when, then
            assertThatThrownBy(() -> userService.updateUser(user.getId(), request))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.USER_DUPLICATED_NICKNAME.getMessage());
        }
    }

    @Nested
    @DisplayName("비밀번호 변경 서비스")
    class updatePassword {

        private User user;

        @BeforeEach
        void setUp() {
            //given
            user = userRepository.save(A.toEntity());
        }

        @Test
        @DisplayName("현재 비밀번호가 틀려 실패한다")
        void failByOldPassword() {
            //given
            PasswordUpdateRequest request = wrongOldPwRequest();

            //when, then
            assertThatThrownBy(() -> userService.updatePassword(user.getId(), request))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.USER_WRONG_PASSWORD.getMessage());
        }

        @Test
        @DisplayName("비밀번호 변경에 성공한다")
        void success() {
            //given
            PasswordUpdateRequest request = request(A);

            //when
            userService.updatePassword(user.getId(), request);

            //then
            assertThat(passwordEncoder.matches(request.getNewPassword(), user.getPasswordValue())).isTrue();
        }
    }

    @Nested
    @DisplayName("사용자 얼굴형 분석 및 업데이트 서비스")
    class updateFaceShape {
        private static final String FILENAME = "hello1.png";
        private static final String NAME = "file";
        private User user;

        @BeforeEach
        void setUpUser() {
            user = userRepository.save(A.toEntity());
        }
        @Test
        @DisplayName("사용자 얼굴형 분석 및 업데이트를 성공한다")
        void success() throws IOException {
            //given
            MultipartFile file = createMultipartFile(FILENAME, NAME);
            given(connector.detectFaceShape(any(MultipartFile.class))).willReturn(Tag.OBLONG);

            //when
            userService.updateFaceShape(user.getId(), file);

            //then
            assertAll(
                    () -> assertThat(user.existFaceShape()).isTrue(),
                    () -> assertThat(user.getFaceShapeTag()).isEqualTo(Tag.OBLONG)
            );
        }

        @Test
        @DisplayName("빈 파일로 실패한다")
        void failByEmptyFile() {
            //given
            MultipartFile emptyFile = createEmptyMultipartFile(NAME);
            ErrorCode expectedError = ErrorCode.EMPTY_FILE_EX;

            given(connector.detectFaceShape(emptyFile)).willThrow(new WishHairException(expectedError));
            given(connector.detectFaceShape(null)).willThrow(new WishHairException(expectedError));

            //when
            assertThatThrownBy(() -> userService.updateFaceShape(user.getId(), emptyFile))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
            assertThatThrownBy(() -> userService.updateFaceShape(user.getId(), emptyFile))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }

        @Test
        @DisplayName("Flask Server 오류로 실패한다")
        void failByFlaskServer() throws IOException {
            //given
            MultipartFile file = createMultipartFile(FILENAME, NAME);
            ErrorCode expectedError = ErrorCode.FLASK_SERVER_EXCEPTION;
            given(connector.detectFaceShape(file)).willThrow(new WishHairException(expectedError));

            //when, then
            assertThatThrownBy(() -> userService.updateFaceShape(user.getId(), file))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }

        @Test
        @DisplayName("Flask Sever 의 잘못된 응답으로 실패한다")
        void failByInvalidResponse() throws IOException {
            //given
            MultipartFile file = createMultipartFile(FILENAME, NAME);
            ErrorCode expectedError = ErrorCode.FLASK_RESPONSE_ERROR;
            given(connector.detectFaceShape(file)).willThrow(new WishHairException(expectedError));

            //when, then
            assertThatThrownBy(() -> userService.updateFaceShape(user.getId(), file))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }
    }

    @Nested
    @DisplayName("비밀번호 갱신 서비스")
    class refreshPassword {
        private static final String NEW_PASSWORD = "hello1234@";
        private static final String WRONG_PASSWORD = "wrong";

        @Test
        @DisplayName("비밀번호 갱신에 성공한다")
        void success() {
            //given
            User user = userRepository.save(A.toEntity());
            PasswordRefreshRequest request = new PasswordRefreshRequest(A.getEmail(), NEW_PASSWORD);

            //when
            userService.refreshPassword(request);

            //then
            assertThat(passwordEncoder.matches(NEW_PASSWORD, user.getPasswordValue())).isTrue();
        }

        @Test
        @DisplayName("이메일에 맞는 사용자가 존재하지 않아 실패")
        void failByNotExistUser() {
            //given
            PasswordRefreshRequest request = new PasswordRefreshRequest(A.getEmail(), NEW_PASSWORD);

            //when, then
            assertThatThrownBy(() -> userService.refreshPassword(request))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.USER_NOT_FOUND_BY_EMAIL.getMessage());
        }

        @Test
        @DisplayName("틀린 비밀번호 형식으로 실패")
        void failByWrongPassword() {
            //given
            userRepository.save(A.toEntity());
            PasswordRefreshRequest wrongRequest = new PasswordRefreshRequest(A.getEmail(), WRONG_PASSWORD);

            //when, then
            assertThatThrownBy(() -> userService.refreshPassword(wrongRequest))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.USER_INVALID_PASSWORD.getMessage());
        }
    }
}