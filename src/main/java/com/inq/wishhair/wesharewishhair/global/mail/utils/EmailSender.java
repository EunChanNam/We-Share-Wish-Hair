package com.inq.wishhair.wesharewishhair.global.mail.utils;

import com.inq.wishhair.wesharewishhair.user.event.RefundMailSendEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private static final String AUTH_MAIL_TITLE = "We-Share-Wish-Hair 이메일 인증";
    private static final String REFUND_MAIL_TITLE = "We-Share-Wish-Hair 포인트 환급 요청";
    private static final String PERSONAL = "We-Share-Wish-Hair";
    private static final String AUTH_TEMPLATE = "AuthMailTemplate";
    private static final String REFUND_TEMPLATE = "RefundRequestMailTemplate";

    @Value("${spring.mail.username}")
    private String from;
    @Value("${mail.point-mail-receiver}")
    private String receiver;
    private final JavaMailSender mailSender;
    private final ITemplateEngine templateEngine;

    public void sendAuthMail(String address, String authKey) throws MessagingException, UnsupportedEncodingException {

        Context context = new Context();
        context.setVariable("authKey", authKey);

        String htmlTemplate = templateEngine.process(AUTH_TEMPLATE, context);

        MimeMessage mimeMessage = generateMessage(address, htmlTemplate, AUTH_MAIL_TITLE);

        mailSender.send(mimeMessage);
    }

    public void sendRefundRequestMail(RefundMailSendEvent event) throws MessagingException, UnsupportedEncodingException {

        Context context = new Context();
        context.setVariable("username", event.username());
        context.setVariable("bankName", event.bankName());
        context.setVariable("accountNumber", event.accountNumber());
        context.setVariable("dealAmount", event.dealAmount());

        String htmlTemplate = templateEngine.process(REFUND_TEMPLATE, context);

        MimeMessage mimeMessage = generateMessage(receiver, htmlTemplate, REFUND_MAIL_TITLE);

        mailSender.send(mimeMessage);
    }

    private MimeMessage generateMessage(String address, String htmlTemplate, String subject) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        mimeMessageHelper.setTo(address);
        mimeMessageHelper.setFrom(new InternetAddress(from, PERSONAL));
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlTemplate, true);
        return mimeMessage;
    }
}
