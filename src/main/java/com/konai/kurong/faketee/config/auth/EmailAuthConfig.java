//package com.konai.kurong.faketee.config.auth;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
//@Configuration
//public class EmailAuthConfig {
//
//    @Bean(name="mailSender")
//    public JavaMailSenderImpl getJavaMailSender() {
//        Properties properties = new Properties();
//        properties.put("mail.smtp.auth", true);
//        properties.put("mail.transport.protocol", "smtp");
//        properties.put("mail.smtp.starttls.enable", true);
//        properties.put("mail.smtp.starttls.required", true);
//        properties.put("mail.debug", true);
//
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("konai.faketee@gmail.com");
//        mailSender.setPassword("josykdczsepovbti");
//        mailSender.setDefaultEncoding("utf-8");
//        mailSender.setJavaMailProperties(properties);
//
//        return mailSender;
//    }
//}