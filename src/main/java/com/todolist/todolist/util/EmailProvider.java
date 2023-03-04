package com.todolist.todolist.util;

import com.todolist.todolist.dao.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class EmailProvider {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "todolistorganization@gmail.com";
        String senderName = "todolist organization";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "todolist organization.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullName());
        String verifyURL = getSiteUrl() + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    private String getSiteUrl() {
        HttpServletRequest httpServletRequest = RequestContextUtil.getServletRequest().orElseThrow(ExceptionUtil::exUnsupported);
        String siteUrl = httpServletRequest.getRequestURL().toString();
        return siteUrl.replace(httpServletRequest.getServletPath(), "");
    }

}
