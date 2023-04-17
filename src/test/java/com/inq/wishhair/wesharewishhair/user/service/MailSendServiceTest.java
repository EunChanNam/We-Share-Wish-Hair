package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.inq.wishhair.wesharewishhair.user.service.utils.MailDtoUtils.mailDto;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MailSendServiceTest extends ServiceTest {

    @Autowired
    private MailSendService mailSendService;

    @Nested
    @DisplayName("이메일 발송 서비스")
    class sendEmail {
        @Test
        @DisplayName("만들어진 메일 정보를 통해 메일을 전송한다")
        void success() {
            //given
            MailDto mailDto = mailDto(UserFixture.A);

            //when, then -> 성공하면 성공적으로 메일이 발송된다.
//            assertDoesNotThrow(() -> mailSendService.sendMail(mailDto));
        }

        @Test
        @DisplayName("중복된 이메일로 400 예외를 던진다")
        void failByDuplicatedEmail() {
            //given
            userRepository.save(UserFixture.A.toEntity());
            MailDto mailDto = mailDto(UserFixture.A);

            //when, then
            assertThatThrownBy(() -> mailSendService.sendMail(mailDto))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.USER_DUPLICATED_EMIAL.getMessage());
        }
    }
}
