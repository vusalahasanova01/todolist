package com.todolist.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class JavaMailSenderConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.office365.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("talib114@outlook.com");
        javaMailSender.setPassword("taliB5836");
        javaMailSender.setProtocol("smtp");
        javaMailSender.setJavaMailProperties(props);
        return javaMailSender;
    }

}
