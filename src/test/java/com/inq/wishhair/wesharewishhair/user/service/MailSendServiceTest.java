package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.inq.wishhair.wesharewishhair.global.fixture.MailDtoFixture.A;

public class MailSendServiceTest extends ServiceTest {

    @Autowired
    private MailSendService mailSendService;

    @Test
    @DisplayName("만들어진 메일 정보를 통해 메일을 전송한다")
    void test1() {
        //given
        MailDto mailDto = A.toMailDto();

        //when, then -> 성공하면 성공적으로 메일이 발송된다.
        mailSendService.sendMail(mailDto);
    }
}
