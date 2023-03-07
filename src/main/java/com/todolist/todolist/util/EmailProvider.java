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

    public void sendRegistrationEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "todolist organization.";
        String verifyURL = getSiteUrl() + "/verify?code=" + user.getVerificationCode();
        String subject = "Please verify your registration";

        sendEmail(user, content, subject, verifyURL);
    }

    public void sendResetPasswordEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"[[URL]]\" target=\"_self\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
        String verifyURL = getSiteUrl() + "/verify/reset-password?token=" + user.getResetPasswordToken();
        String subject = "Here's the link to reset your password";

        sendEmail(user, content, subject, verifyURL);
    }

    private void sendEmail(User user, String content, String subject, String verifyUrl) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "todolistorganization@gmail.com";
        String senderName = "todolist organization";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullName());

        content = content.replace("[[URL]]", verifyUrl);

        helper.setText(content, true);

        mailSender.send(message);
    }

    private String getSiteUrl() {
        HttpServletRequest httpServletRequest = RequestContextUtil.getServletRequest().orElseThrow(ExceptionUtil::exUnsupported);
        String siteUrl = httpServletRequest.getRequestURL().toString();
        return siteUrl.replace(httpServletRequest.getServletPath(), "");
    }

}
