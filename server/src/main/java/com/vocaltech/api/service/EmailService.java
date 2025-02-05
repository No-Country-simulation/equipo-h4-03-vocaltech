//package com.vocaltech.api.service;
//
//import com.vocaltech.api.service.interfaces.IEmailService;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.nio.charset.StandardCharsets;
//
//@Service
//public class EmailService implements IEmailService {
//    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
//
//   @Value("${mail.username}")
//   private String emailUser;
//
//    private final JavaMailSender javaMailSender;
//
//    @Autowired
//    public EmailService(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//
//
//    @Override
//    public void sendEmail(String toUser, String subject, String message) {
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//
//        mailMessage.setFrom(emailUser);
//        mailMessage.setTo(toUser);
//        mailMessage.setSubject(subject);
//        mailMessage.setText(message);
//
//        javaMailSender.send(mailMessage);
//
//
//    }
//
//
//    @Override
//    public void sendEmailWithFile(String toUser, String subject, String message, list<File file) {
//
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
//
//            mimeMessageHelper.setFrom(emailUser);
//            mimeMessageHelper.setTo(toUser);
//            mimeMessageHelper.setSubject(subject);
//            mimeMessageHelper.setText(message, true);
//
//            if (file != null && file.exists()) {
//                mimeMessageHelper.addAttachment(file.getName(), file);
//            }
//
//            javaMailSender.send(mimeMessage);
//
//
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//}